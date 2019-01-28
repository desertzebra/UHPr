/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.lstore;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class MedicalFragmentIndex {

    private String gid;
    private String fragmentId;
    private String fragmentName;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    @Override
    public String toString() {
        return "MedicalFragmentIndex{" + "gid=" + gid + ", fragmentId=" + fragmentId + ", fragmentName=" + fragmentName + '}';
    }
    
}
