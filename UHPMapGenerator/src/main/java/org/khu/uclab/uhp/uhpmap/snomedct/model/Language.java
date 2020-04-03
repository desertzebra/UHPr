/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.snomedct.model;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Language {
    private String name;
    private int count;

    @Override
    public String toString() {
        return "Language{" + "name=" + name + ", count=" + count + '}';
    }

    public Language() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
    
    
}
