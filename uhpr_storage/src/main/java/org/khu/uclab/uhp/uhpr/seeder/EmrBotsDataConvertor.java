/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.seeder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.khu.uclab.uhp.uhpr.entities.Emrbots_AdmissionsCorePopulatedTable;
import org.khu.uclab.uhp.uhpr.entities.Emrbots_AdmissionsDiagnosesCorePopulatedTable;
import org.khu.uclab.uhp.uhpr.entities.Emrbots_LabsCorePopulatedTable;
import org.khu.uclab.uhp.uhpr.entities.Emrbots_PatientCorePopulatedTable;
import org.khu.uclab.uhp.uhpr.entities.MedicalFragment;
import org.khu.uclab.uhp.uhpr.lstore.Lstore;
import org.khu.uclab.uhp.uhpr.lstore.MedicalFragmentIndex;
import org.khu.uclab.uhp.uhpr.lstore.UhprIndex;
import org.khu.uclab.uhp.uhpr.seeder.util.NameGenerator;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class EmrBotsDataConvertor {

    String savepath = "F:\\Projects\\UHP\\v3_7_data";
    String readpath = "F:\\Projects\\UHP\\emrbot\\100000-Patients\\";
    Lstore lstore = new Lstore();
    ArrayList<MedicalFragment> medDataArchive = new ArrayList<>();
    long unixTimestamp = Instant.now().getEpochSecond();
    NameGenerator nameGen = new NameGenerator();

    @Override
    public String toString() {
        return "consumeDataEmrBots{" + "savepath=" + savepath + ", readpath=" + readpath + ", lstore=" + lstore + ", medDataArchive=" + medDataArchive + ", unixTimestamp=" + unixTimestamp + ", nameGen=" + nameGen + '}';
    }
        ArrayList<MedicalFragment> userData = new ArrayList();
        ArrayList<MedicalFragmentIndex> userMetaData = new ArrayList();
        
    public void consume() {
        System.out.println("seeding lstore...");

        CsvHandler emrBotsDemographicReader = new CsvHandler();
        CsvHandler emrBotsAdmissionReader = new CsvHandler();
        CsvHandler emrBotsDiagnoseReader = new CsvHandler();
        CsvHandler emrBotsLabsReader = new CsvHandler();
        emrBotsDemographicReader.setSourceFile(new File(readpath + "PatientCorePopulatedTable.txt"));
        emrBotsAdmissionReader.setSourceFile(new File(readpath + "AdmissionsCorePopulatedTable.txt"));
        emrBotsDiagnoseReader.setSourceFile(new File(readpath + "AdmissionsDiagnosesCorePopulatedTable.txt"));
        emrBotsLabsReader.setSourceFile(new File(readpath + "LabsCorePopulatedTable.txt"));

        try {
            //First Reading the demographics file
            ArrayList<HashMap<String, Object>> data = emrBotsDemographicReader.readData();
            data.forEach((tuple) -> {
                UhprIndex ui = new UhprIndex();

                int gender = ((String) tuple.get("PatientGender")).equalsIgnoreCase("male") ? 0 : 1;
                ui.setGender(gender);
                String[] splitName = nameGen.getRandomName(gender).split(" ");
                ui.setLastName(splitName[splitName.length - 1]);
                String fName = "";
                for (int i = splitName.length - 2; i > -1; i--) {
                    fName += splitName[i];
                }
                ui.setFirstName(fName);
                ui.setDOB(((String) tuple.get("PatientDateOfBirth")));
                UUID uuid = UUID.randomUUID();
                ui.setGid(((String) tuple.get("PatientID")));
                if(((String) tuple.get("PatientID"))==null){
                    System.out.println("PatientID is null:"+tuple);
                }
                // Add only 1 EMRBOT demographic entry
                MedicalFragmentIndex medFragmentIndex = new MedicalFragmentIndex();
                medFragmentIndex.setGid(ui.getGid());
                medFragmentIndex.setFragmentName(new FragmentTypes().getEmrbotsDemographicFragmentName());
                //Generate a uuid
                medFragmentIndex.setFragmentId(UUID.randomUUID().toString());

                Emrbots_PatientCorePopulatedTable medFrag
                        = new Emrbots_PatientCorePopulatedTable(
                                medFragmentIndex.getFragmentId(),
                                medFragmentIndex.getFragmentName(), String.valueOf(unixTimestamp));
                medFrag.setValues(tuple);

                // Now generating the rest of the medical fragments for indexing
                    userMetaData.add(medFragmentIndex);
                    userData.add(medFrag);
                    //this.medDataArchive.add(medFrag);
                //patient_gid.put((String) tuple.get("PatientId"), ui.getGid());
                //System.out.println(ui);
                lstore.addUhprItem(ui);

            });
            
            System.out.print("saving lstore...");

            File file = new File(savepath);
            file.mkdirs();

            String realpath = savepath + "\\" + "uhpridx_" + unixTimestamp + ".csv";
            BufferedWriter fw1 = new BufferedWriter(new FileWriter(realpath, true));
            lstore.getUhprList().forEach((PatientIndex) -> {
                try {
                    fw1.write(PatientIndex.getFirstName());
                    fw1.write('|');
                    fw1.write(PatientIndex.getLastName());
                    fw1.write('|');
                    fw1.write(PatientIndex.getDOB());
                    fw1.write('|');
                    fw1.write(PatientIndex.getGid());
                    fw1.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fw1.close();
            
            
            //lstore.getUhprItem(ui);
            //Now the Rest
            data = emrBotsAdmissionReader.readData();
            System.out.println("Admission Core File has :"+data.size()+" rows");
            data.forEach((tuple) -> {
                String gid = (String) tuple.get("PatientID");

                //Add this medical FragmentSS
                MedicalFragmentIndex medFragmentIndex = new MedicalFragmentIndex();
                medFragmentIndex.setGid(gid);
                medFragmentIndex.setFragmentName(new FragmentTypes().getFragmentName(3));
                //Generate a uuid
                medFragmentIndex.setFragmentId(UUID.randomUUID().toString());

                Emrbots_AdmissionsCorePopulatedTable medFrag
                        = new Emrbots_AdmissionsCorePopulatedTable(
                                medFragmentIndex.getFragmentId(),
                                medFragmentIndex.getFragmentName(), String.valueOf(unixTimestamp));
                medFrag.setValues(tuple);
                //System.out.println(gid);
                // Now generating the rest of the medical fragments for indexing
//                if (userMetaData.containsKey(gid)) {
//                    userMetaData.get(gid).add(medFragmentIndex);
//                } else {
//                    System.out.println("gid:"+gid);
//                    System.out.println(userMetaData.keySet().size());
//                    System.out.println("ERROR GID does not exist");
//                }
//                if (userData.containsKey(gid)) {
//                    userData.get(gid).add(medFrag);
//                } else {
//                    System.out.println("ERROR GID does not exist");
//                }
//                this.medDataArchive.add(medFrag);

                    userMetaData.add(medFragmentIndex);
                    userData.add(medFrag);

            });
            
            //Saving the Admissions Core file only
            this.saveFiles();
            
            data = emrBotsDiagnoseReader.readData();
            System.out.println("Admission Diagnosis File has :"+data.size()+" rows");
            data.forEach((tuple) -> {
                String gid = (String) tuple.get("PatientID");

                //Add this medical FragmentSS
                MedicalFragmentIndex medFragmentIndex = new MedicalFragmentIndex();
                medFragmentIndex.setGid(gid);
                medFragmentIndex.setFragmentName(new FragmentTypes().getFragmentName(4));
                //Generate a uuid
                medFragmentIndex.setFragmentId(UUID.randomUUID().toString());

                Emrbots_AdmissionsDiagnosesCorePopulatedTable medFrag
                        = new Emrbots_AdmissionsDiagnosesCorePopulatedTable(
                                medFragmentIndex.getFragmentId(),
                                medFragmentIndex.getFragmentName(), String.valueOf(unixTimestamp));
                medFrag.setValues(tuple);

                // Now generating the rest of the medical fragments for indexing
                                    userData.add(medFrag);
                                     userMetaData.add(medFragmentIndex);
            });
            //Saving the Admissions Diagnosis Core file only
            this.saveFiles();
            
            int start = 0;
            int limit = 50000;
            
            while(data!=null){
                data = emrBotsLabsReader.readData(start, limit);
            System.out.println("Collected :"+data.size()+" rows from Lab File");
            data.forEach((tuple) -> {
                String gid = (String) tuple.get("PatientID");

                //Add this medical FragmentSS
                MedicalFragmentIndex medFragmentIndex = new MedicalFragmentIndex();
                medFragmentIndex.setGid(gid);
                medFragmentIndex.setFragmentName(new FragmentTypes().getFragmentName(5));
                //Generate a uuid
                medFragmentIndex.setFragmentId(UUID.randomUUID().toString());

                Emrbots_LabsCorePopulatedTable medFrag
                        = new Emrbots_LabsCorePopulatedTable(
                                medFragmentIndex.getFragmentId(),
                                medFragmentIndex.getFragmentName(), String.valueOf(unixTimestamp));
                medFrag.setValues(tuple);

                // Now generating the rest of the medical fragments for indexing
                userData.add(medFrag);
                                     userMetaData.add(medFragmentIndex);
            });
            this.saveFiles();
            System.out.println("Saved records from:"+start+" to "+ (start+limit));
            start=start+limit;
            }
            

        //lstore.setUhprList(new LstoreSeeder().generateFields());
        System.out.println("done");

          
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
    
    public void saveFiles() throws IOException{
          String realpath = savepath + "\\" + "medicalfragmentidx_" + unixTimestamp + ".csv";
            BufferedWriter fw2 = new BufferedWriter(new FileWriter(realpath, true));
            userMetaData.forEach((medFragIndex)->{
                try {
                            //System.out.println(gid + "," + medFragIndex);
                            fw2.write(medFragIndex.getGid());
                            fw2.write('|');
                            fw2.write(medFragIndex.getFragmentId());
                            fw2.write('|');
                            fw2.write(medFragIndex.getFragmentName());
                            fw2.write('\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            });
                
            fw2.close();
            this.userMetaData.clear();
            
            System.out.println("done");
            System.out.print("saving uhpr...");

            File file = new File(savepath);
            file.mkdirs();

            realpath = savepath + "\\" + "uhpr_" + unixTimestamp + ".csv";
            BufferedWriter fw3 = new BufferedWriter(new FileWriter(realpath, true));
            userData.forEach((med) -> {
                        try {
                            //System.out.println(med);
                            fw3.write(med.getFragmentId());
                            fw3.write('|');
                            fw3.write(med.getFragmentType());
                            fw3.write('|');
                            fw3.write(med.getData());
                            fw3.write('|');
                            fw3.write(med.getVersion());
                            fw3.write('\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            fw3.close();
        this.userData.clear();
            
    
    }
    
    
    
    public void generateJSON(String path) {
        System.out.println("Done");
    }

    public void showMedicalFragmentIndexes() {
        lstore.getUhprList().forEach((PatientIndex) -> {
            PatientIndex.getMedicalFragments().forEach((medFragIndex) -> {
                System.out.println("Index:"+medFragIndex.toString());
            });
        });
    }

    public void showMedicalFragments() {
        medDataArchive.forEach((fragment) -> {
            System.out.println("Fragment:"+fragment.toString());
        });
    }

}
