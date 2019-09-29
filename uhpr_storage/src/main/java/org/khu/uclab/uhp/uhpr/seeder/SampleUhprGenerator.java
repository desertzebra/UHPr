package org.khu.uclab.uhp.uhpr.seeder;


import java.util.ArrayList;
import java.time.Instant;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import org.khu.uclab.uhp.uhpr.entities.*;
import org.khu.uclab.uhp.uhpr.lstore.Lstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleUhprGenerator {
	
	String savepath = "F:\\Projects\\UHP\\v3_7_data";
	
    Lstore lstore = new Lstore();
    ArrayList<MedicalFragment> medDataArchive = new ArrayList<>();
    long unixTimestamp = Instant.now().getEpochSecond();
    
    @Override
    public String toString() {
        return "SampleUhprGenerator{" + "lstore=" + lstore + ", medDataArchive=" + medDataArchive + '}';
    }
    
    public void seedLstore(){
    	System.out.print("seeding lstore...");
        lstore.setUhprList(new LstoreSeeder().generateFields());
        System.out.println("done");
        
        System.out.print("saving lstore...");
        try {
	        File file = new File(savepath);
	        file.mkdirs();
	        
	        String realpath = savepath + "\\" + "uhpridx_"+unixTimestamp+".csv";
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
	        
	        realpath = savepath + "\\" + "medicalfragmentidx_"+unixTimestamp+".csv";
	        BufferedWriter fw2 = new BufferedWriter(new FileWriter(realpath, true));
	        lstore.getUhprList().forEach((PatientIndex) -> {
	        	PatientIndex.getMedicalFragments().forEach((medFragIndex) -> {
	        		try {
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
	        });
	        fw2.close();
	        		
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.out.println("done");
    }
    
    public void seedMedicalFragments(){
        System.out.print("seeding medicalfragments...");
        lstore.getUhprList().forEach((PatientIndex) -> {
            PatientIndex.getMedicalFragments().forEach((medFragIndex)-> {
                MedicalFragment fragment = null;
                
                
                // "Krsiloemr", "openemr_Demographics", "openemr_MedicalProblems"
                if(medFragIndex.getFragmentName().equalsIgnoreCase("Krsiloemr")){
                	fragment = new Krsiloemr_tblPatient(medFragIndex.getFragmentId(), medFragIndex.getFragmentName(), String.valueOf(unixTimestamp));
                        fragment.setValues(null);
                        fragment.setPrivate(PatientIndex.getFirstName(), PatientIndex.getLastName(), PatientIndex.getDOB(), PatientIndex.getGender());	// match the the data in (name, dob, gender) if certain fragment contains
                        fragment.setData();
                }
                else if(medFragIndex.getFragmentName().equalsIgnoreCase("openemr_Demographics")){
                	fragment = new Openemr_Demographics(medFragIndex.getFragmentId(), medFragIndex.getFragmentName(), String.valueOf(unixTimestamp));
                        fragment.setValues(null);
                        fragment.setPrivate(PatientIndex.getFirstName(), PatientIndex.getLastName(), PatientIndex.getDOB(), PatientIndex.getGender());	// match the the data in (name, dob, gender) if certain fragment contains
                        fragment.setData();
                }
                else if(medFragIndex.getFragmentName().equalsIgnoreCase("openemr_MedicalProblems")){
                	fragment = new Openemr_MedicalProblems(medFragIndex.getFragmentId(), medFragIndex.getFragmentName(), String.valueOf(unixTimestamp));
                        fragment.setValues(null);
                        fragment.setPrivate(PatientIndex.getFirstName(), PatientIndex.getLastName(), PatientIndex.getDOB(), PatientIndex.getGender());	// match the the data in (name, dob, gender) if certain fragment contains
                        fragment.setData();
                }
                else if(medFragIndex.getFragmentName().equalsIgnoreCase("openemr_Prescription")){
                	fragment = new Openemr_Prescription(medFragIndex.getFragmentId(), medFragIndex.getFragmentName(), String.valueOf(unixTimestamp));
                        fragment.setValues(null);
                        fragment.setPrivate(PatientIndex.getFirstName(), PatientIndex.getLastName(), PatientIndex.getDOB(), PatientIndex.getGender());	// match the the data in (name, dob, gender) if certain fragment contains
                        fragment.setData();
                }
                else {
                    System.out.println("Incorrect Fragment name:"+medFragIndex.getFragmentName());
                    System.exit(1);
                }
                
                
                
            	medDataArchive.add(fragment);
            });
        });
        System.out.println("done");
        
        System.out.print("saving uhpr...");
        try {
	        File file = new File(savepath);
	        file.mkdirs();
	        
	        String realpath = savepath + "\\" + "uhpr_"+unixTimestamp+".csv";
	        BufferedWriter fw3 = new BufferedWriter(new FileWriter(realpath, true));
	        medDataArchive.forEach((med) -> {
	        	try {
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
        } catch(Exception e) {
        	e.printStackTrace();
        }
        System.out.println("done");
    }
	
	public void generateJSON(String path) {
		System.out.println("Done");
	}
	
	public void showMedicalFragmentIndexes() {
		lstore.getUhprList().forEach((PatientIndex) -> {
            PatientIndex.getMedicalFragments().forEach((medFragIndex)-> {
            	System.out.println(medFragIndex.toString());
            });
		});
	}
	
	public void showMedicalFragments() {
		medDataArchive.forEach((fragment) -> {
			System.out.println(fragment.toString());
		});
	}
        
        
        public void loadLstore(){
    	System.out.print("loading data from csv files to lstore...");
        
        
        //lstore.setUhprList(new LstoreSeeder().generateFields());
        System.out.println("done");
        
        System.out.print("saving lstore...");
        try {
	        File file = new File(savepath);
	        file.mkdirs();
	        
	        String realpath = savepath + "\\" + "uhpridx_"+unixTimestamp+".csv";
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
	        
	        realpath = savepath + "\\" + "medicalfragmentidx_"+unixTimestamp+".csv";
	        BufferedWriter fw2 = new BufferedWriter(new FileWriter(realpath, true));
	        lstore.getUhprList().forEach((PatientIndex) -> {
	        	PatientIndex.getMedicalFragments().forEach((medFragIndex) -> {
	        		try {
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
	        });
	        fw2.close();
	        		
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.out.println("done");
    }
        
        
}
