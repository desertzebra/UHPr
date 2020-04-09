/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.model;

import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Concept {
    private String id;
    private String name;
    private String source;

    public Concept(String id, String name, String source) {
        this.id = id;
        this.name = name;
        this.source = source;
    }

    public Concept(String ui, String rootSource) {
        this.id = ui;
        this.source = rootSource;
    }

    @Override
    public String toString() {
        return "Concept{" + "id=" + id + ", name=" + name + ", source=" + source + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.source);
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
        final Concept other = (Concept) obj;
        if(!this.id.equalsIgnoreCase(other.id)) return false;
        if(!this.name.equalsIgnoreCase(other.name)) return false;
        if(!this.source.equalsIgnoreCase(other.source)) return false;
        
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    
    
}
