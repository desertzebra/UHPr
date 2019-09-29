/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.seeder;

import java.util.ArrayList;
import java.util.UUID;
import org.khu.uclab.uhp.uhpr.lstore.MedicalFragmentIndex;
import org.khu.uclab.uhp.uhpr.lstore.UhprIndex;
import org.khu.uclab.uhp.uhpr.seeder.util.DateGenerator;
import org.khu.uclab.uhp.uhpr.seeder.util.GenderGenerator;
import org.khu.uclab.uhp.uhpr.seeder.util.NameGenerator;
import org.khu.uclab.uhp.uhpr.seeder.util.RandomGenerator;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class LstoreSeeder {

    private int NumOfRecords = 1;
    private int NumOfFragmentsPerRecord = 40;

    RandomGenerator rand = new RandomGenerator();
    NameGenerator nameGen = new NameGenerator();
    DateGenerator dateGen = new DateGenerator();
    GenderGenerator genderGen = new GenderGenerator();

    /**
     * Randomly generate a list of patient records
     * @return List of UhprIndex
     */
    public ArrayList<UhprIndex> generateFields() {
        ArrayList<UhprIndex> uhprRecords = new ArrayList<UhprIndex>();
        
        for(int i=0;i<NumOfRecords;i++){
            uhprRecords.add(generatePatientProfile());
        }
        return uhprRecords;
    }
    
    /**
     * Naive indexing, which identifies patients by their firstname, lastname,
     * and date of birth
     *
     * @return UhprIndex
     */
    public UhprIndex generatePatientProfile() {
        UhprIndex patient = new UhprIndex();

        //Generate metadata for the patient index
        int gender = genderGen.getRandomGender();
        String fullName = nameGen.getRandomName(gender);
        String dob = dateGen.getRandomBirthDateByAge(rand.randInt(5, 90));
        //String dob="19880708";
        //int gender = 0;
        //String fullName = "Harry Potter";
        
        String[] splitName = fullName.split(" ");
        UUID uuid = UUID.randomUUID();

        patient.setFirstName(splitName[0]);
        patient.setLastName(splitName[1]);
        patient.setDOB(dob);
        patient.setGid(uuid.toString());
        patient.setGender(gender);
        
        // Add only 1 openEMR Demographics
        MedicalFragmentIndex medFragment = new MedicalFragmentIndex();
        medFragment.setGid(patient.getGid());
        medFragment.setFragmentName(new FragmentTypes().getOpenEmrDemographicFragmentName());
        //Generate a uuid
        medFragment.setFragmentId(UUID.randomUUID().toString());

        patient.getMedicalFragments().add(medFragment);
        
        // Now generating the rest of the medical fragments for indexing
        for (int j = 0; j < NumOfFragmentsPerRecord-1; j++) {
            medFragment = new MedicalFragmentIndex();
            medFragment.setGid(patient.getGid());
            medFragment.setFragmentName(new FragmentTypes().getRandomFragmentName());
            //Generate a uuid
            medFragment.setFragmentId(UUID.randomUUID().toString());
            
            patient.getMedicalFragments().add(medFragment);
        }

        return patient;
    }

    public int getNumOfRecords() {
        return NumOfRecords;
    }

    public void setNumOfRecords(int NumOfRecords) {
        this.NumOfRecords = NumOfRecords;
    }

    public int getNumOfFragmentsPerRecord() {
        return NumOfFragmentsPerRecord;
    }

    public void setNumOfFragmentsPerRecord(int NumOfFragmentsPerRecord) {
        this.NumOfFragmentsPerRecord = NumOfFragmentsPerRecord;
    }

}
