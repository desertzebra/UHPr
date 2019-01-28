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
    String[] AllFragmentNames = {"Krsiloemr", "openemr_Demographics", "openemr_MedicalProblems", "openemr_Prescription"};
    String[] FragmentNames = {"Krsiloemr", "openemr_MedicalProblems", "openemr_Prescription"};	// except demographic
    String DemographicFragmentName = "openemr_Demographics";
    
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

    String getDemographicFragmentName() {
        return this.DemographicFragmentName;
    }
    
}
