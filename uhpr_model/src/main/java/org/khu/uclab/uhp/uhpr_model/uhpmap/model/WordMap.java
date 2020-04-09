/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr_model.uhpmap.model;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class WordMap {
    private Word wordLeft;
    private Word wordRight;
    private SchemaRelation relationship;
    private String method;
    private String comments;
    private double confidence;

    public WordMap(Word wordLeft, Word wordRight, SchemaRelation relationship, String method, double confidence) {
        this.wordLeft = wordLeft;
        this.wordRight = wordRight;
        this.relationship = relationship;
        this.method = method;
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "WordMap{" + "wordLeft=" + wordLeft.getTitle() + ", wordRight=" + wordRight.getTitle() + ", relationship=" + relationship + ", method=" + method + ", comments=" + comments + ", confidence=" + confidence + '}';
    }

    

    public SchemaRelation getRelationship() {
        return relationship;
    }

    public void setRelationship(SchemaRelation relationship) {
        this.relationship = relationship;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public Word getWordLeft() {
        return wordLeft;
    }

    public void setWordLeft(Word wordLeft) {
        this.wordLeft = wordLeft;
    }

    public Word getWordRight() {
        return wordRight;
    }

    public void setWordRight(Word wordRight) {
        this.wordRight = wordRight;
    }
    
    
}
