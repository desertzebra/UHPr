/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.seeder;

import org.khu.uclab.uhp.uhpr.seeder.util.RandomGenerator;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class FragmentTypes {
    private final String[] AllFragmentNames = {"Krsiloemr", "openemr_Demographics",
        "openemr_MedicalProblems", "openemr_Prescription",
        "emrbots_PatientCorePopulatedTable", "emrbots_AdmissionCorePopulatedTable",
        "emrbots_AdmissionsDiagnosesCorePopulatedTable",
        "emrbots_LabsCorePopulatedTable"};
    private final String[] FragmentNames = {"Krsiloemr", "openemr_MedicalProblems",
        "openemr_Prescription","emrbots_AdmissionCorePopulatedTable",
        "emrbots_AdmissionsDiagnosesCorePopulatedTable",
        "emrbots_LabsCorePopulatedTable"};	// except demographic
    private final String openEmrDemographicFragmentName = "openemr_Demographics";
    private final String emrbotsDemographicFragmentName = "emrbots_PatientCorePopulatedTable";
    
    public String getRandomFragmentName(){
        return this.FragmentNames[(new RandomGenerator()).randInt(FragmentNames.length)];
    }
    
    public String getFragmentName(int index){
        if(index>-1 & index < FragmentNames.length){
            return this.FragmentNames[index];
        }else{
            return this.FragmentNames[0];
        }
    }

    String getOpenEmrDemographicFragmentName() {
        return this.openEmrDemographicFragmentName;
    }

    public String getEmrbotsDemographicFragmentName() {
        return emrbotsDemographicFragmentName;
    }
    
}
