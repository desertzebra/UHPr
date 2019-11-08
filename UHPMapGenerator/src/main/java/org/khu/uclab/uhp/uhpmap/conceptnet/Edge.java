/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.conceptnet;

import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Edge {

    private String lookupStr;

    // Strings identifying the edge properties in the JSON string.
    private static final String REL = "rel";
    private static final String RELATION2 = "related";
    private static final String WEIGHT = "weight";
    private static final String SURFACE_TEXT = "surfaceText";
    private static final String DATASET = "dataset";
    private static final String START = "start";
    private static final String END = "end";

    // This Edge's properties.
    private String relationString = "";
    private Relation relation = Relation.Other;
    private double weight = 0.0;
    private String surfaceText = "";
    private Dataset dataset;
    private String startNode = "";
    private String endNode = "";

    
   
    public Edge(String lookupString, JSONObject jsonObj) throws JSONException {
        lookupStr = lookupString.trim().toLowerCase();

        if (jsonObj.has(REL)) {
            relation = setRelation(cleanRelation(jsonObj.getJSONObject(REL).getString("@id")));
        }
        if (jsonObj.has(RELATION2)) {
            relation = setRelation(cleanRelation(jsonObj.getJSONObject(REL).getString("@id")));
        }
        if (jsonObj.has(WEIGHT)) {
            weight = jsonObj.getDouble(WEIGHT);
        }
        if (jsonObj.has(START)) {
            startNode = jsonObj.getJSONObject(START).getString("@id");
        }
        if (jsonObj.has(END)) {
            endNode = jsonObj.getJSONObject(END).getString("@id");
        }
        if (jsonObj.has(DATASET)) {
            dataset = Dataset.getDataset(jsonObj.getString(DATASET));
        }
        if (!jsonObj.has(SURFACE_TEXT) || jsonObj.isNull(SURFACE_TEXT)) {
            surfaceText = null;
        } else {
            surfaceText = cleanSurfaceText(jsonObj.getString(SURFACE_TEXT));
        }
    }

    private Relation setRelation(String relationStr) {
        relationString = relationStr;
        Relation rel = null;
        try {
            rel = Relation.valueOf(relationStr);
        } catch (IllegalArgumentException e) {
            // The relation isn't in our Relation enum. No problem—we deal with this below.
        }
        if (rel == null) {
            rel = Relation.Other;
        }
        return rel;
    }

    private String cleanRelation(String string) {
        return string.replaceFirst("/r/", "");
    }

    // Surface text often appears with square brackets, e.g. "[[Strep throat]] is [[painful]]"
    private String cleanSurfaceText(String string) {
        String TO_REMOVE = "(\"|\\[|\\])";
        return string.replaceAll(TO_REMOVE, "");
    }

    public String getLookupStr() {
        return lookupStr;
    }

    public void setLookupStr(String lookupStr) {
        this.lookupStr = lookupStr;
    }

    public String getRelationString() {
        return relationString;
    }

    public void setRelationString(String relationString) {
        this.relationString = relationString;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSurfaceText() {
        return surfaceText;
    }

    public void setSurfaceText(String surfaceText) {
        this.surfaceText = surfaceText;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

}
