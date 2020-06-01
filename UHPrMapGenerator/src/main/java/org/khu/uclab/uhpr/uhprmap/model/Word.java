/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Word {
    private String title;
    private String conceptNetString;
    private List<Concept> umlsConcept;
    private String snomedCtString;

    Word(String term) {
        this.title = term;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConceptNetString() {
        return conceptNetString;
    }

    public void setConceptNetString(String conceptNetString) {
        this.conceptNetString = conceptNetString;
    }

    public List<Concept> getUmlsConcept() {
        return umlsConcept;
    }

    public void setUmlsConcept(List<Concept> umlsConcept) {
        this.umlsConcept = umlsConcept;
    }

    public String getSnomedCtString() {
        return snomedCtString;
    }

    public void setSnomedCtString(String snomedCtString) {
        this.snomedCtString = snomedCtString;
    }

    @Override
    public String toString() {
        return "Word{" + "title=" + title + ", umlsConcept=" + umlsConcept + ", snomedCtString=" + snomedCtString + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.conceptNetString);
        hash = 97 * hash + Objects.hashCode(this.umlsConcept);
        hash = 97 * hash + Objects.hashCode(this.snomedCtString);
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
        final Word other = (Word) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.conceptNetString, other.conceptNetString)) {
            return false;
        }
        if (!Objects.equals(this.snomedCtString, other.snomedCtString)) {
            return false;
        }
        if (!Objects.equals(this.umlsConcept, other.umlsConcept)) {
            return false;
        }
        return true;
    }

    
}
