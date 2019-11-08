/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap;

import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class AttributeMap {
    private Schema nodeLeft;
    private Schema nodeRight;
    private SchemaRelation relationship;
    private String method;
    private String comments;
    private double confidence;

    public Schema getNodeLeft() {
        return nodeLeft;
    }

    public void setNodeLeft(Schema nodeLeft) {
        this.nodeLeft = nodeLeft;
    }

    public Schema getNodeRight() {
        return nodeRight;
    }

    public void setNodeRight(Schema nodeRight) {
        this.nodeRight = nodeRight;
    }

    @Override
    public String toString() {
        return "SchemaBridge{" + "nodeLeft=" + nodeLeft + ", nodeRight=" + nodeRight + ", relationship=" + relationship + ", method=" + method + ", comments=" + comments + ", confidence=" + confidence + '}';
    }


    public AttributeMap(Schema nodeLeft, Schema nodeRight, SchemaRelation relationship,String method, double confidence) {
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
        this.relationship = relationship;
        this.method = method;
        this.confidence = confidence;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nodeLeft);
        hash = 97 * hash + Objects.hashCode(this.nodeRight);
        hash = 97 * hash + Objects.hashCode(this.relationship);
        hash = 97 * hash + Objects.hashCode(this.method);
        hash = 97 * hash + Objects.hashCode(this.comments);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.confidence) ^ (Double.doubleToLongBits(this.confidence) >>> 32));
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
        final AttributeMap other = (AttributeMap) obj;
        if (Double.doubleToLongBits(this.confidence) != Double.doubleToLongBits(other.confidence)) {
            return false;
        }
        if (!Objects.equals(this.method, other.method)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.nodeLeft, other.nodeLeft)) {
            return false;
        }
        if (!Objects.equals(this.nodeRight, other.nodeRight)) {
            return false;
        }
        if (this.relationship != other.relationship) {
            return false;
        }
        return true;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

  
    
}
