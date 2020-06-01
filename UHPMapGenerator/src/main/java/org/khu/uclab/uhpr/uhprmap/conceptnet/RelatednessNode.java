/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.conceptnet;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class RelatednessNode {
    private ArrayList<String> context;
    private String id;
    private double weight;
    private ArrayList<RelatednessNode> related;

    public RelatednessNode() {
        context = new ArrayList();
        id = "";
        weight = 0.0;
        related = new ArrayList();
    }
    
    public RelatednessNode(JSONObject jsonObj){
        this();
        //System.out.println(jsonObj);
        if(jsonObj.has(ConceptNetKeys.CONTEXT)) {
            //System.out.println(jsonObj.getJSONArray(ConceptNetKeys.CONTEXT));
            jsonObj.getJSONArray(ConceptNetKeys.CONTEXT).forEach((Object contextItem) -> {
                //System.out.println(contextItem);
                context.add((String) contextItem);
            });
        }
        if(jsonObj.has(ConceptNetKeys.ID)) {
            id = jsonObj.getString(ConceptNetKeys.ID);
        }
        if(jsonObj.has(ConceptNetKeys.VALUE)) {
            weight = jsonObj.getDouble(ConceptNetKeys.VALUE);
        }
        if(jsonObj.has(ConceptNetKeys.RELATED)) {
            jsonObj.getJSONArray(ConceptNetKeys.RELATED).forEach((relatedItem)->{
                RelatednessNode rn = new RelatednessNode((JSONObject) relatedItem);
                related.add(rn);
            });
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
    
    @Override
    public String toString() {
        return "RelatednessNode{" + "context=" + context + ", id=" + id + ", weight=" + weight + ", related=" + related + '}';
    }

    public ArrayList<String> getContext() {
        return context;
    }

    public void setContext(ArrayList<String> context) {
        this.context = context;
    }

    public ArrayList<RelatednessNode> getRelated() {
        return related;
    }

    public void setRelated(ArrayList<RelatednessNode> related) {
        this.related = related;
    }
    
    
}
