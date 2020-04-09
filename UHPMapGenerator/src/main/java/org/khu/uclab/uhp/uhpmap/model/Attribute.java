/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Attribute {
    private String tableName;
    private String fullString;
    private List<Word> words = new ArrayList<>();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.tableName);
        hash = 29 * hash + Objects.hashCode(this.fullString);
        hash = 29 * hash + Objects.hashCode(this.words);
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
        final Attribute other = (Attribute) obj;
        if (!this.tableName.equalsIgnoreCase(other.tableName)) {
            return false;
        }
        if (!this.fullString.equalsIgnoreCase(other.fullString)) {
            return false;
        }
        return Objects.equals(this.words, other.words);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTableName() {
        return tableName;
    }

    public String getFullString() {
        return fullString;
    }

    public void setFullString(String fullString) {
        this.fullString = fullString;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
    
    public void setWords(String[] candidateTerms){
        for(String term: candidateTerms){
            this.words.add(new Word(term));
        }
    }

    @Override
    public String toString() {
        return "Attribute{" + "tableName=" + tableName + ", fullString=" + fullString + ", words=" + words + '}';
    }

    

    
}
