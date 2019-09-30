/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.conceptnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class ConceptNetWrapper {
    private static final String CONCEPTNET_URI = "http://api.conceptnet.io/";
    private static final String NBR_TO_RETRIEVE = "100";
    
    // Strings identifying properties in the JSON string.
    private static final String EDGES = "edges";
    private static final String NUM_FOUND = "numFound";
       
    public HashMap<Relation, Edge> query(String token){
        
        token = token.trim();
        
        // Data on the response for a ConceptNet lookup.
        HashMap<Relation, Edge> edges = new HashMap();
        String qStr = "";
        try {
            qStr = CONCEPTNET_URI + "c/en/" + token + "?limit=" + NBR_TO_RETRIEVE ;//+ "&format=json";
            
            
            // Get Json String from ConceptNet
            String json = getJsonString(qStr);
            
            //System.out.println(json);
            JSONTokener jsonTokener = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject(jsonTokener);
            //int numFound = jsonObject.getInt(NUM_FOUND);

            // Each JSONArray element contains data on one edge of the many edges returned. 
            JSONArray resultArray = jsonObject.getJSONArray(EDGES);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject result = resultArray.getJSONObject(i);
                try{
                    Edge edge = new Edge(token, result);
                    edges.put(edge.getRelation(), edge);
                }catch(JSONException e){
                    System.out.println(result);
                    e.printStackTrace();
                    System.out.println("JSONException in Edge constructor for string: " + token);
                }
                
            }
         return edges;   
        } catch (IOException e) {
            System.out.println("IOException: Can't retrieve message(IO Exception) for: " + qStr);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSONException: Can't retrieve message for(JSON Exception): " + qStr);
        }
        return null;
    }
    
    public ArrayList<Edge> queryRelatedTo(String startToken, String endToken){
        
        startToken = startToken.trim().toLowerCase();
        endToken = endToken.trim().toLowerCase();
        
        // Data on the response for a ConceptNet lookup.
        ArrayList<Edge> edges = new ArrayList();
        String qStr = "";
        try {
            qStr = CONCEPTNET_URI + "/relatedness?node1=c/en/" + startToken + "&node2=c/en/" + endToken + "&limit=" + NBR_TO_RETRIEVE ;//+ "&format=json";
            
            
            // Get Json String from ConceptNet
            
            String json = getJsonString(qStr);
            //rate limit
            Thread.sleep(1000);
            
            if(json.isEmpty()){
                System.out.println("Empty JSON response");
                return null; 
            }
            //System.out.println(json);
            JSONTokener jsonTokener = new JSONTokener(json);
            JSONObject jsonObject;
            try{
                 jsonObject = new JSONObject(jsonTokener);
            }catch(JSONException e){
                System.out.println("Exception:"+e.getMessage());
                System.out.println("original reply:"+json);
                return null;
            }
            //int numFound = jsonObject.getInt(NUM_FOUND);
            if(jsonObject.get("error")!=null){
                System.out.println(jsonObject.get("error"));
                return null;
            }
            // Each JSONArray element contains data on one edge of the many edges returned. 
            JSONArray resultArray = jsonObject.getJSONArray(EDGES);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject result = resultArray.getJSONObject(i);
                try{
//                    Edge edge1 = new Edge(startToken, result);
//                    Edge edge2 = new Edge(endToken, result);
                    edges.add(new Edge(startToken, result));
                    
                }catch(JSONException e){
                    System.out.println(result);
                    e.printStackTrace();
                    System.out.println("JSONException in Edge constructor for string: " + startToken+"/"+endToken);
                }
                
            }
         return edges;   
        } catch (IOException e) {
            System.out.println("IOException: Can't retrieve message(IO Exception) for: " + qStr);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSONException: Can't retrieve message for(JSON Exception): " + qStr);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConceptNetWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getJsonString(String queryURI) throws IOException {
        BufferedReader reader = getReaderForQuery(queryURI);
        if(reader == null)  {
            return "";
        }
        
        StringBuffer buffer = new StringBuffer();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)   {
            buffer.append(chars, 0, read);  
        }
        return buffer.toString();
    }
    
//    private BufferedReader getReaderForQuery(String query) throws IOException     {
//        URL url = new URL(query);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        return reader;
//    }
    
     public BufferedReader  getReaderForQuery(String url) {
        
        OutputStream outputStream = null;
        
        HttpURLConnection httpConnection = null;
        String jsonString = null;
        try {
            httpConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
          
            InputStream inputStream = httpConnection.getInputStream();
            StringBuilder responseBuilder = new StringBuilder();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return bufferedReader;
            
        } catch (MalformedURLException ex) {
            System.err.println("MalformedURLException");
            System.err.println(ex.getMessage());
        } catch (ProtocolException ex) {
            System.err.println("ProtocolException");
            System.err.println(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("UnsupportedEncodingException");
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IOException");
            System.err.println(ex.getMessage());
        }
        
        return null;
}
    
}
