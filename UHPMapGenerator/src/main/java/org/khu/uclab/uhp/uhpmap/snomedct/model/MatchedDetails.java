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
public class MatchedDetails {
    private int total;
    private int skipTo;
    private int returnLimit;

    public MatchedDetails() {
    }

    @Override
    public String toString() {
        return "MatchedDetails{" + "total=" + total + ", skipTo=" + skipTo + ", returnLimit=" + returnLimit + '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkipTo() {
        return skipTo;
    }

    public void setSkipTo(int skipTo) {
        this.skipTo = skipTo;
    }

    public int getReturnLimit() {
        return returnLimit;
    }

    public void setReturnLimit(int returnLimit) {
        this.returnLimit = returnLimit;
    }
    
    
}
