/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr_model.util;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.khu.uclab.uhp.uhpr_model.UhprModel;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class CsvHandler{
  
    private File targetDataFile;
    private File targetMetaFile;

    private UhprModel uhprmodel;
    CSVParser parser;
    
    public CsvHandler() {
        targetDataFile = null;
        targetMetaFile = null;
        uhprmodel = new UhprModel();
        parser = new CSVParserBuilder().withSeparator('|').build();
        
    }
    
    public void readMetaData() throws IOException {

        CSVReader csvMetadata = new CSVReaderBuilder(new FileReader(targetMetaFile)).withCSVParser(parser).build();
        
        Iterator<String[]> iterator = csvMetadata.iterator();
        String[] headers = iterator.next();
        
        //default indexes are already set
        int medFragNameIndex = 0;
        int medFragIndex = 1;
        int firstnameIndex = 2; 
        int lastnameIndex = 3;
        int dobIndex = 4;
        int gidIndex = 5;
        
        for(int i=0;i<headers.length;i++){
            if(null == headers[i]){
                System.out.println("Fatal ERROR!! Unknown meta header name("+headers[i]+")");
            }else switch (headers[i]) {
                case "medicalfragmentidx.fragmentname":
                    medFragNameIndex = i;
                    break;
                case "medicalfragmentidx.fragmentid":
                    medFragIndex = i;
                    break;
                case "uhpridx.gid":
                    gidIndex = i;
                    break;
                case "uhpridx.firstname":
                    firstnameIndex = i;
                    break;
                case "uhpridx.lastname":
                    lastnameIndex = i;
                    break;
                case "uhpridx.dob":
                    dobIndex = i;
                    break;
                default:
                    System.out.println("Fatal ERROR!! Unknown meta header name("+headers[i]+")");
                    break;
            }
        }
        
        boolean isMetaRead = false;
        while (iterator.hasNext()) {
            String[] metaData = iterator.next();
            if(!isMetaRead){
                uhprmodel.setiUhpr(metaData[gidIndex]);
                uhprmodel.setName(metaData[firstnameIndex]+ " "+ metaData[lastnameIndex]);
                uhprmodel.setDob(metaData[dobIndex]);
                isMetaRead = true;
            }
            uhprmodel.addMedFragTypeIndex(metaData[medFragNameIndex], metaData[medFragIndex]);
        }
    }
    
    /**
     * reads a list of String[] as a comma separated
     * line in the target file.
     * @throws IOException
     */
    public void readData() throws IOException {

        CSVReader csvMetadata = new CSVReaderBuilder(new FileReader(targetDataFile)).withCSVParser(parser).build();
        
        Iterator<String[]> iterator = csvMetadata.iterator();
        String[] headers = iterator.next();
     
        //default indexes are already set
        int medFragId = 0;
        int medFragType = 1;
        int data = 2; 
        int version = 3;
        
        for(int i=0;i<headers.length;i++){
            if(null == headers[i]){
                System.out.println("Fatal ERROR!! Unknown data header("+headers[i]+")");
            }else switch (headers[i]) {
                case "uhpr.fragmentid":
                    medFragId = i;
                    break;
                case "uhpr.fragmenttype":
                    medFragType = i;
                    break;
                case "uhpr.data":
                    data = i;
                    break;
                case "uhpr.version":
                    version = i;
                    break;
                default:
                    System.out.println("Fatal ERROR!! Unknown data header ("+headers[i]+")");
                    break;
            }
        }
        
        while (iterator.hasNext()) {
            String[] medFragments = iterator.next();
            uhprmodel.addMedFragInstance(medFragments[medFragId], medFragments[medFragType], medFragments[version], medFragments[data]);
        }
    }
    
    
    public File getTargetDataFile() {
        return targetDataFile;
    }

    public void setTargetDataFile(File targetDataFile) {
        this.targetDataFile = targetDataFile;
    }


    public File getTargetMetaFile() {
        return targetMetaFile;
    }

    public void setTargetMetaFile(File targetMetaFile) {
        this.targetMetaFile = targetMetaFile;
    }

    public UhprModel getUhprmodel() {
        return uhprmodel;
    }

    public void setUhprmodel(UhprModel uhprmodel) {
        this.uhprmodel = uhprmodel;
    }

    
}