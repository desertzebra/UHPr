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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.khu.uclab.uhp.uhpmap.AttributeMap;
import org.khu.uclab.uhp.uhpmap.SchemaRelation;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class ConceptNetWrapper {

    private static final String CONCEPTNET_URI = "http://163.180.116.210/conceptnet";
    private static final String NBR_TO_RETRIEVE = "100";

    // Strings identifying properties in the JSON string.
    private static final String EDGES = "edges";
    private static final int RETRIES = 3;
    private final String LANGUAGE = "/c/en/";
    private HashMap<String,HashMap<Relation, Edge>> singleTokenLookupTable = new HashMap();
    private final long RETRY_DELAY_MS = 100;
    
    public HashMap<Relation, Edge> query(String token) {

        token = token.trim();
        if(singleTokenLookupTable.containsKey(token)){
            return singleTokenLookupTable.get(token);
        }
        
        // This token was not queried earlier, so let's do a ConceptNet lookup now.
        HashMap<Relation, Edge> edges = new HashMap();
        String qStr = "";
        try {
            qStr = CONCEPTNET_URI+ LANGUAGE + token + "?limit=" + NBR_TO_RETRIEVE;//+ "&format=json";
            System.out.println(qStr);
            // Get Json String from ConceptNet
            String json = getJsonString(qStr);
            System.out.println(json);
            //System.out.println(json);
            JSONTokener jsonTokener = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject(jsonTokener);
            //int numFound = jsonObject.getInt(NUM_FOUND);

            // Each JSONArray element contains data on one edge of the many edges returned. 
            JSONArray resultArray = jsonObject.getJSONArray(EDGES);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject result = resultArray.getJSONObject(i);
                try {
                    Edge edge = new Edge(token, result);
                    edges.put(edge.getRelation(), edge);
                } catch (JSONException e) {
                    System.out.println(result);
                    e.printStackTrace();
                    System.out.println("JSONException in Edge constructor for string: " + token);
                }

            }
            if(edges.size()>0){
                singleTokenLookupTable.put(token, edges);
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
    
    /**
     * Get the weight of the relationship between start node and end node
     *
     * @param startToken Left word
     * @param endToken Right word
     * @return RelatednessNodes
     */
    public RelatednessNode queryRelatedness(String startToken, String endToken){
        startToken = startToken.trim().toLowerCase();
        endToken = endToken.trim().toLowerCase();
        String qStr = "";
        RelatednessNode rn = null;

        try {
            qStr = CONCEPTNET_URI + "/relatedness?node1=" + LANGUAGE + startToken + "&node2=" + LANGUAGE + endToken + "&limit=" + NBR_TO_RETRIEVE;//+ "&format=json";

            // Get relatedness Json String from ConceptNet
            JSONObject jsonObject = getResponse(qStr);
            if (jsonObject != null){
                rn = new RelatednessNode(jsonObject);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSONException: Can't retrieve message for(JSON Exception): " + qStr);
        }
        return rn;
    }
    
    /**
     * Get the related terms between start node and end node
     *    
     * @param startToken Left word
     * @param endToken Right word
     * @return ArrayList of RelatednessNodes
     */
    public ArrayList<RelatednessNode> queryRelatedTerms(String startToken, String endToken){
        startToken = startToken.trim().toLowerCase();
        endToken = endToken.trim().toLowerCase();
        String qStr = "";
        ArrayList<RelatednessNode> relatedWeights = new ArrayList();
        try {
            qStr = CONCEPTNET_URI + "/related" + LANGUAGE + startToken + "_" + endToken + "&filter=" + LANGUAGE;

            // Get related terms Json String from ConceptNet
            JSONObject jsonObject_relatedTerms = getResponse(qStr);
            if (jsonObject_relatedTerms != null) {
                        // Get the related terms
                        JSONArray resultArray = jsonObject_relatedTerms.getJSONArray(ConceptNetKeys.RELATED);
                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject result = resultArray.getJSONObject(i);
                            try {
                                relatedWeights.add(new RelatednessNode(result));
                            } catch (JSONException e) {
                                System.out.println(result);
                                e.printStackTrace();
                                System.out.println("JSONException in Edge constructor for string: " + startToken + "/" + endToken);
                                return null;
                            }

                        }
                    } else {
                        return null;
                    }
        }catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSONException: Can't retrieve message for(JSON Exception): " + qStr);
        }
        return relatedWeights;
    }
    
    /**
     * Get the value of LANGUAGE
     *
     * @return the value of LANGUAGE
     */
    public String getLANGUAGE() {
        return LANGUAGE;
    }

    private JSONObject getResponse(String queryUri) {
        JSONObject jsonObject = null;
        if (!"".equals(queryUri)) {
            try {
                String response = this.getJsonString(queryUri);
                if (response.isEmpty()) {
                    System.out.println("Empty JSON response");
                    return null;
                } else {
                    //System.out.println(json);
                    JSONTokener jsonTokener = new JSONTokener(response);

                    try {
                        jsonObject = new JSONObject(jsonTokener);

                        if (jsonObject.has("error")) {
                            System.out.println("Error in JSON response:" + jsonObject.get("error"));
                            return null;
                        }
                    } catch (JSONException e) {
                        System.out.println("Exception:" + e.getMessage());
                        System.out.println("original reply:" + response);
                        return null;
                    }
                }
            } catch (IOException ex) {
                System.out.println("Exception:" + ex.getMessage());
                return null;
            }
        }
        return jsonObject;
    }

    private String getJsonString(String queryURI) throws IOException {
        BufferedReader reader = getReaderForQuery(queryURI);
        if (reader == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1) {
            buffer.append(chars, 0, read);
        }
        return buffer.toString();
    }

    public BufferedReader getReaderForQuery(String url) {
//        try {
//            //rate limit
//            Thread.sleep(RETRY_DELAY_MS);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ConceptNetWrapper.class.getName()).log(Level.SEVERE, null, ex);
//        }
        boolean isConnected = false;
        HttpURLConnection httpConnection = null;
        try {
            connectionRetries: for(int retry=0; retry <= RETRIES && !isConnected; retry ++){
                if (retry > 0) {
                   //rate limit
                   System.out.println("retry " + retry + "/" + RETRIES);
                    try {
                        Thread.sleep(RETRIES * RETRY_DELAY_MS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConceptNetWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                httpConnection = (HttpURLConnection) ((new URL(url).openConnection()));
                httpConnection.setDoOutput(true);
                httpConnection.setRequestProperty("Content-Type", "application/json");
                httpConnection.setRequestProperty("Accept", "application/json");
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                switch (httpConnection.getResponseCode()) {
                    case HttpURLConnection.HTTP_OK:
                        isConnected = true;
                        break; // fine, go on
                    case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                        System.out.println(" **gateway timeout**");
                        break;// retry
                    case HttpURLConnection.HTTP_UNAVAILABLE:
                        System.out.println(url + " **unavailable** ");
                        break;// retry, server is unstable
                    default:
                        System.out.println(url + " **unknown response code**.");
                        break connectionRetries; // abort
                }
            }
            if(isConnected){
                InputStream inputStream = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                return bufferedReader;
            }else{
                return null;
            }

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
