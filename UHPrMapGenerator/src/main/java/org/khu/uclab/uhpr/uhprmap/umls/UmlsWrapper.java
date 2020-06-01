/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.umls;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.module.Configuration;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.khu.uclab.uhpr.uhprmap.JsonReader;
import org.khu.uclab.uhpr.uhprmap.model.Concept;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class UmlsWrapper {

    private static final String UMLS_REST_URI = "https://uts-ws.nlm.nih.gov";
    private String apiKey = "a5970fde-aafc-4410-9115-e5e541d30620";
    private final ObjectMapper jsonReader;
    RestTicketClient ticketClient = new RestTicketClient(apiKey);
    //get a ticket granting ticket for this session.
    String tgt = ticketClient.getTgt();
    String version = "current";

    public UmlsWrapper() {
        this.jsonReader = new ObjectMapper();
    }

    public List<Concept> query(String token) throws IOException {

        token = token.trim();
        try {
            token = URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(UmlsWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        int total = 0;
        int pageNumber = 0;
        JSONArray items;
        List<Concept> expandedTerm = new ArrayList();
        //StringBuilder expandedTerm = new StringBuilder("<<");
        //System.out.println("Fetching results for term:" + token);
        
        resultsComplete: do {
            pageNumber++;
            StringBuilder qStr = new StringBuilder();
            qStr.append(UMLS_REST_URI).append("/rest/search/")
                    .append(version)
                    .append("?ticket=").append(ticketClient.getST(tgt))
                    .append("&pageNumber=").append(pageNumber)
                    .append("&searchType=exact")
                    .append("&string=").append(token);
            //System.out.println("[UMLS][Query] String:" + qStr.toString());
            JSONObject snomedCtResult = JsonReader.readJsonFromUrl(qStr.toString());
            //System.out.println("[UMLS][Query] Result:" + snomedCtResult);
            items = snomedCtResult.getJSONObject("result").getJSONArray("results");
            
            pageComplete: for (int itemIte = 0; itemIte < items.length() ; itemIte++) {
                if(items.getJSONObject(itemIte).getString("ui").equalsIgnoreCase("NONE")){
                    break resultsComplete;
                }
                String ui = items.getJSONObject(itemIte).getString("ui");
                String name = items.getJSONObject(itemIte).getString("name");
                String rootSource = items.getJSONObject(itemIte).getString("rootSource");
                expandedTerm.add(new Concept(ui,name,rootSource));
//                expandedTerm.append(ui);
//                expandedTerm.append(",");
//                expandedTerm.append(name);
//                expandedTerm.append(",");
//                expandedTerm.append(rootSource);
//                expandedTerm.append("||");      // Separator between different terms

            }
        } while (items.length() > 0 && items.getJSONObject(items.length() - 1).getString("ui").equalsIgnoreCase("NONE"));
                    // Close the special format
        //String cui = "<<" + items.getJSONObject(itemIte).getJSONObject("concept").getString("conceptId") + ">>";
        //System.out.println("Found in UMLS CT:" + token + ", result is:" + expandedTerm);
        //return cui;

        return expandedTerm;
    }

    private String getJsonString(String queryURI) throws IOException {
        BufferedReader reader = getReaderForQuery(queryURI);
        if (reader == null) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1) {
            buffer.append(chars, 0, read);
        }
        return buffer.toString();
    }

//    private BufferedReader getReaderForQuery(String query) throws IOException     {
//        URL url = new URL(query);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        return reader;
//    }
    public BufferedReader getReaderForQuery(String url) {

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
