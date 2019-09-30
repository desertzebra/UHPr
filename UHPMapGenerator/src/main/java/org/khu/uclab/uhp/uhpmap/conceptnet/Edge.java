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
    private static final String RELATION = "rel";
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

    @Override
    public String toString() {
        return "Edge{" + "lookupStr=" + lookupStr + ", relationString=" + relationString + ", relation=" + relation + ", weight=" + weight + ", surfaceText=" + surfaceText + ", dataset=" + dataset + ", startNode=" + startNode + ", endNode=" + endNode + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.lookupStr);
        hash = 37 * hash + Objects.hashCode(this.relationString);
        hash = 37 * hash + Objects.hashCode(this.relation);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.surfaceText);
        hash = 37 * hash + Objects.hashCode(this.dataset);
        hash = 37 * hash + Objects.hashCode(this.startNode);
        hash = 37 * hash + Objects.hashCode(this.endNode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.startNode, other.startNode)) {
            return false;
        }
        if (!Objects.equals(this.endNode, other.endNode)) {
            return false;
        }
        if (this.relation != other.relation) {
            return false;
        }
        if (this.dataset != other.dataset) {
            return false;
        }
        return true;
    }
    
    public Edge(String lookupString, JSONObject jsonObj) throws JSONException {
        lookupStr = lookupString.trim().toLowerCase();
        
            relation = setRelation(cleanRelation(jsonObj.getJSONObject(RELATION).getString("@id")));
            weight = jsonObj.getDouble(WEIGHT);
            startNode = jsonObj.getJSONObject(START).getString("@id");
            endNode = jsonObj.getJSONObject(END).getString("@id");
            dataset = Dataset.getDataset(jsonObj.getString(DATASET));       
            if(jsonObj.isNull(SURFACE_TEXT)){
                surfaceText = null; 
            }else{
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
        if(rel == null) {
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
