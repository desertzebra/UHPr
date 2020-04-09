/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr_model.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.khu.uclab.uhp.uhpr_model.entities.MedicalFragment;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class UhprModel {
    private String iUhpr;
    private String name;
    private String dob;
    public Map<String,ArrayList<String>> medFragTypes = new HashMap<>();
    
    public ArrayList<MedicalFragmentModel> medFragInstances = new ArrayList();

    public void addMedFragInstance(String fragmentId, String fragmentType, String version, String data){
        MedicalFragment mf = new MedicalFragment(fragmentId, fragmentType, version);
        mf.setData(data);
        MedicalFragmentModel mfm = new MedicalFragmentModel();
        mfm.addMedFragType(version, mf);
        medFragInstances.add(mfm);
    }
    public void addMedFragType(String name){
        if(!medFragTypes.containsKey(name)){
            medFragTypes.put(name, new ArrayList());
        }
    }
    public void addMedFragTypeIndex(String name, String index){
        if(!medFragTypes.containsKey(name)){
            this.addMedFragType(name);
        }
        medFragTypes.get(name).add(index);
        
    }
    public void addMedFragInstances(MedicalFragment medFrag){
        
    }
    public String getiUhpr() {
        return iUhpr;
    }

    public void setiUhpr(String iUhpr) {
        this.iUhpr = iUhpr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "UhprModel{" + "iUhpr=" + iUhpr + ", name=" + name + ", dob=" + dob + ", medFragTypes=" + medFragTypes + ", medFragInstances=" + medFragInstances + '}';
    }

    
    
    
    
    
}
