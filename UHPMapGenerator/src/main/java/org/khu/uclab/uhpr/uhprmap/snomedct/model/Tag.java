/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.snomedct.model;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Tag {
    private String name;
    private int count;

    @Override
    public String toString() {
        return "Tag{" + "name=" + name + ", count=" + count + '}';
    }

    public Tag() {
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
