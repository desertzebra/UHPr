/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.entities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Emrbots_PatientCorePopulatedTable extends MedicalFragment {
    private String PatientId;
    private String PatientGender;
    private String PatientDateOfBirth;
    private String PatientRace;
    private String PatientMaritalStatus;
    private String PatientLanguage;
    private String PatientPopulationPercentageBelowPoverty;
    private String PatientName;

    public Emrbots_PatientCorePopulatedTable(String fragmentId, String fragmentType, String version) {
        super(fragmentId, fragmentType, version);
    }
    
    @Override
    public void setValues(HashMap<String,Object> values) {
        //System.out.println(values.keySet());
        this.PatientId = (String) values.get("PatientID");
        this.PatientGender = (String) values.get("PatientGender");
        this.PatientDateOfBirth = (String) values.get("PatientDateOfBirth");
        this.PatientLanguage = (String) values.get("PatientLanguage");
        this.PatientMaritalStatus = (String) values.get("PatientMaritalStatus");
        this.PatientRace = (String) values.get("PatientRace");
        this.PatientPopulationPercentageBelowPoverty = (String) values.get("PatientPopulationPercentageBelowPoverty");
    }

    @Override
    public void setPrivate(String firstName, String lastName, String dob, int gender) {
        PatientName = firstName + " " + lastName;
        if(gender==1){
            PatientGender = "male";
        }else{
            PatientGender = "female";
        }
        PatientDateOfBirth = dob;
    }

    @Override
    public void setData() {
        this.setData(this.toString());
    }

    @Override
    public String toString() {
        return "PatientId=" + PatientId + ", PatientGender=" + PatientGender + ", PatientDateOfBirth=" + PatientDateOfBirth + ", PatientRace=" + PatientRace + ", PatientMaritalStatus=" + PatientMaritalStatus + ", PatientLanguage=" + PatientLanguage + ", PatientPopulationPercentageBelowPoverty=" + PatientPopulationPercentageBelowPoverty + ", PatientName=" + PatientName;
    }

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatientGender() {
        return PatientGender;
    }

    public void setPatientGender(String PatientGender) {
        this.PatientGender = PatientGender;
    }

    public String getPatientDateOfBirth() {
        return PatientDateOfBirth;
    }

    public void setPatientDateOfBirth(String PatientDateOfBirth) {
        this.PatientDateOfBirth = PatientDateOfBirth;
    }

    public String getPatientRace() {
        return PatientRace;
    }

    public void setPatientRace(String PatientRace) {
        this.PatientRace = PatientRace;
    }

    public String getPatientMaritalStatus() {
        return PatientMaritalStatus;
    }

    public void setPatientMaritalStatus(String PatientMaritalStatus) {
        this.PatientMaritalStatus = PatientMaritalStatus;
    }

    public String getPatientLanguage() {
        return PatientLanguage;
    }

    public void setPatientLanguage(String PatientLanguage) {
        this.PatientLanguage = PatientLanguage;
    }

    public String getPatientPopulationPercentageBelowPoverty() {
        return PatientPopulationPercentageBelowPoverty;
    }

    public void setPatientPopulationPercentageBelowPoverty(String PatientPopulationPercentageBelowPoverty) {
        this.PatientPopulationPercentageBelowPoverty = PatientPopulationPercentageBelowPoverty;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }


    
    

}
