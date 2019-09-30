/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap;

/**
 *
 * @author Fahad Ahmed Satti
 */
public enum SchemaRelation {
    EQUAL(0, "="),
    NOTEQUAL(1000, "!");
    
    
    private final Integer key;
    private final String value;


    SchemaRelation(Integer key, String value) {
        this.value = value;
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

   
}
