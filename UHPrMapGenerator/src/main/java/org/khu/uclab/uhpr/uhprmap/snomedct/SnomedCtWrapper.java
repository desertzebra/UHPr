/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.snomedct;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.khu.uclab.uhpr.uhprmap.JsonReader;

import org.khu.uclab.uhpr.uhprmap.snomedct.rf2.MatchResults;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class SnomedCtWrapper {

    private static final String SNOMED_REST_URI = "https://browser.ihtsdotools.org/snowstorm/snomed-ct/browser/MAIN/2020-03-09/descriptions?&limit=100&active=true&conceptActive=true&lang=english";
//    private static final String EDITION = "en-edition";
//    private static final String RELEASE = "v20180131";
//    private static final String NBR_TO_RETRIEVE = "100";
    private final ObjectMapper jsonReader;

    public SnomedCtWrapper() {
        this.jsonReader = new ObjectMapper();
    }

    public String query(String token) throws IOException {

        token = token.trim();
        try {
            token = URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SnomedCtWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        // Data on the response for a ConceptNet lookup.
        //HashMap<Relation, Edge> edges = new HashMap();
        StringBuilder qStr = new StringBuilder();
            qStr.append(SNOMED_REST_URI);
            qStr.append("&term=");
            qStr.append(token);
            JSONObject snomedCtResult = JsonReader.readJsonFromUrl(qStr.toString());
            JSONArray items = snomedCtResult.getJSONArray("items");
            System.out.println("[SNOMED][Query] String:" + qStr.toString());
            if (items.length() > 0) {
                String conceptId = "<<" + items.getJSONObject(0).getJSONObject("concept").getString("conceptId") + ">>";
                System.out.println("Found in SNOMED CT:" + token+": result is"+conceptId);
                return conceptId;
            }else{
                System.out.println("Not Found in SNOMED CT:" + token);
                System.out.println("result:" + snomedCtResult);
            }

        return null;
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
