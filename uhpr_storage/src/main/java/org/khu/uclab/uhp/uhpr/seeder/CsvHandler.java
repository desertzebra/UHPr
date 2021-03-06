/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.seeder;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.khu.uclab.uhp.uhpr.lstore.MedicalFragmentIndex;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class CsvHandler{
    
    /*
     * target file to store the data
     */
    private File targetFile;
    private File sourceFile;
    CSVParser parser;
    
    private List<String[]> data;
    
    public CsvHandler() {
        targetFile = null;
        data = new ArrayList<>();
        parser = new CSVParserBuilder().withSeparator('\t').build();
    }
    
    
    /**
     * stores a list of String[] as a comma separated
     * line in the target file.
     * @throws IOException
     */
    public void writeData() throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(targetFile));
        
        // write a string[] as a single line
        data.stream().forEach((line) -> {
            writer.writeNext(line);
        });
    }
    
    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public List<String[]> getData() {
        return data;
    }

    
    public void setData(List<MedicalFragmentIndex> index) {
        for(MedicalFragmentIndex med: index){
            //this.data.add(med.getAsArray());
        }
    }
    
    
    /**
     * reads a list of String[] as a comma separated
     * line in the target file.
     * @return Array of tuples, each containing a map of key-value pairs
     * @throws IOException
     */
    public ArrayList<HashMap<String,Object>> readData() throws IOException {

        CSVReader csvData = new CSVReaderBuilder(new FileReader(sourceFile)).withCSVParser(parser).build();
        
        Iterator<String[]> iterator = csvData.iterator();
        String[] headers = iterator.next();
        if(headers[0].trim().equalsIgnoreCase("ï»¿PatientID")) headers[0] = "PatientID";
        if(headers[0].trim().equalsIgnoreCase("癤풮atientID")) headers[0] = "PatientID";
        
        
        ArrayList<HashMap<String,Object>> tuples = new ArrayList();
        while (iterator.hasNext()) {
            HashMap<String, Object> rowData = new HashMap();
            
            String[] cellData = iterator.next();
            if(headers.length!=cellData.length){
                System.out.println(headers[0]);
                System.out.println(cellData[0]);
                System.out.println("Error! header length("+headers.length+") is not equal to tuple length("+cellData.length+")");
            }
            for(int i=0; i<headers.length;i++){
                rowData.put(headers[i].trim(), cellData[i].trim());
            }
            tuples.add(rowData);
        }
        return tuples;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }
    String[] headers;
    ArrayList<HashMap<String, Object>> readData(int start, int limit) throws FileNotFoundException {
        CSVReader csvData = new CSVReaderBuilder(new FileReader(sourceFile)).withCSVParser(parser).build();
        
        Iterator<String[]> iterator = csvData.iterator();
        System.out.println("Iterator ready for:"+start+" to "+limit);
        
        if(start==0){
            System.out.println("Getting header");
            headers = iterator.next();     
            if(headers[0].trim().equalsIgnoreCase("ï»¿PatientID")) headers[0] = "PatientID";
            if(headers[0].trim().equalsIgnoreCase("癤풮atientID")) headers[0] = "PatientID";
        }
        int rowCount = 0;
        while(rowCount<start){
            if(iterator.hasNext()) {
                iterator.next();
                rowCount++;
            }
            else return null;
        }
        System.out.println("Skipped rows:"+rowCount);
        rowCount = 0;
        ArrayList<HashMap<String,Object>> tuples = new ArrayList();
        while(rowCount<limit && iterator.hasNext()){
            //System.out.println("processing row:"+rowCount);
            HashMap<String, Object> rowData = new HashMap();
            
            String[] cellData = iterator.next();
            if(headers.length!=cellData.length){
                System.out.println(headers[0]);
                System.out.println(cellData[0]);
                System.out.println("Error! header length("+headers.length+") is not equal to tuple length("+cellData.length+")");
            }
            for(int i=0; i<headers.length;i++){
                rowData.put(headers[i].trim(), cellData[i].trim());
            }
            tuples.add(rowData);
            rowCount++;
        }
        return tuples;
    }
    
}