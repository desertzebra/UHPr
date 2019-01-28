/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.lstore;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class UhprIndex {

    private String firstName;
    private String lastName;
    private String DOB;
    private String gid;
    private int gender;	// not showed
    private List<MedicalFragmentIndex> medicalFragments;

    public UhprIndex() {
        medicalFragments = new ArrayList<MedicalFragmentIndex>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getGender() {
    	return gender;
    }
    
    public void setGender(int gender) {
    	this.gender = gender;
    }
    
    public List<MedicalFragmentIndex> getMedicalFragments() {
        return medicalFragments;
    }

    public void setMedicalFragments(List<MedicalFragmentIndex> medicalFragments) {
        this.medicalFragments = medicalFragments;
    }

    @Override
    public String toString() {
        return "UhprIndex{" + "firstName=" + firstName + ", lastName=" + lastName + ", DOB=" + DOB + ", gid=" + gid + ", medicalFragments=" + medicalFragments + '}';
    }

}
