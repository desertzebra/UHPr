/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.model;

import org.khu.uclab.uhp.uhpmap.model.Schema;
import java.util.Objects;
import org.khu.uclab.uhp.uhpmap.SchemaRelation;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class SchemaBridge {
    private Schema nodeLeft;
    private Schema nodeRight;
    private SchemaRelation relationship;
    private String method;
    private String comments;

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
        return "{" + nodeLeft + "}==|"+relationship.name()+"|"+method+"|"+comments+"|=={" + nodeRight + "}";
    }

    public SchemaBridge(Schema nodeLeft, Schema nodeRight, SchemaRelation relationship,String method) {
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
        this.relationship = relationship;
        this.method = method;
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
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.nodeLeft);
        hash = 79 * hash + Objects.hashCode(this.nodeRight);
        hash = 79 * hash + Objects.hashCode(this.relationship);
        hash = 79 * hash + Objects.hashCode(this.method);
        hash = 79 * hash + Objects.hashCode(this.comments);
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
        final SchemaBridge other = (SchemaBridge) obj;
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
    
    
}
