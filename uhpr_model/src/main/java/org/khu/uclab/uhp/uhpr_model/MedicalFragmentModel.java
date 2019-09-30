/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr_model;

import java.util.HashMap;
import java.util.Map;
import org.khu.uclab.uhp.uhpr_model.entities.MedicalFragment;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class MedicalFragmentModel {
    //version and the medical fragment
    private Map<String,MedicalFragment> medicalFragmentItem = new HashMap();

    public void addMedFragType(String version, MedicalFragment medFrag){
        medicalFragmentItem.put(version, medFrag);
    }
    public Map<String,MedicalFragment> getMedicalFragmentItem() {
        return medicalFragmentItem;
    }

    public void setMedicalFragmentItem(Map<String,MedicalFragment> medicalFragmentItem) {
        this.medicalFragmentItem = medicalFragmentItem;
    }

    @Override
    public String toString() {
        return "MedicalFragmentModel{" + "medicalFragmentItem=" + medicalFragmentItem + '}';
    }
    
    
    
}
