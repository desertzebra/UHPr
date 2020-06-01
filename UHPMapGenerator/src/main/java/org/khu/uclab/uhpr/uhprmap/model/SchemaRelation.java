/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.model;

/**
 *
 * @author Fahad Ahmed Satti
 */
public enum SchemaRelation {
    EQUAL(1.0, "="),
    AMBIGIOUS(0.5," IS A "),
    SUBSUMPTION(0.7," IS A "),
    NOTEQUAL(0.0, "!");
    
    
    private final Double confidence;
    private final String label;


    SchemaRelation(Double key, String value) {
        this.label = value;
        this.confidence = key;
    }

    public Double getConfidence() {
        return confidence;
    }

    public String getLabel() {
        return label;
    }
}
