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
public class Emrbots_AdmissionsDiagnosesCorePopulatedTable extends MedicalFragment {
    private String PatientId;
    private String AdmissionId;
    private String PrimaryDiagnosisCode;
    private String PrimaryDiagnosisDescription;

    public Emrbots_AdmissionsDiagnosesCorePopulatedTable(String fragmentId, String fragmentType, String version) {
        super(fragmentId, fragmentType, version);
    }
    
    @Override
    public void setValues(HashMap<String,Object> values) {
        this.PatientId = (String) values.get("PatientID");
        this.AdmissionId = (String) values.get("AdmissionID");
        this.PrimaryDiagnosisCode = (String) values.get("PrimaryDiagnosisCode");
        this.PrimaryDiagnosisDescription = (String) values.get("PrimaryDiagnosisDescription");

    }

    @Override
    public void setPrivate(String firstName, String lastName, String dob, int gender) {
        
    }

    @Override
    public void setData() {
        this.setData(this.toString());
    }

    @Override
    public String toString() {
        return "PatientId=" + PatientId + ", AdmissionId=" + AdmissionId + ", PrimaryDiagnosisCode=" + PrimaryDiagnosisCode + ", PrimaryDiagnosisDescription=" + PrimaryDiagnosisDescription;
    }

    

}
