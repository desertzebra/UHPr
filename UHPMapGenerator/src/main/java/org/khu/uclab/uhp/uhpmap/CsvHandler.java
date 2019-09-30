/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final int TABLE_NAME_INDEX = 0;
    private final int ATTRIBUTE_NAME_INDEX = 1;
    
    public CsvHandler() {
        targetFile = null;
        parser = new CSVParserBuilder().withSeparator(',').build();
    }
    

    
    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    /**
     * reads a list of String[] as a comma separated
     * line in the target file.
     * @return Array of tuples, each containing a map of key-value pairs
     * @throws IOException
     */
    public ArrayList<Schema> readData() throws IOException {

        CSVReader csvData = new CSVReaderBuilder(new FileReader(sourceFile)).withCSVParser(parser).build();
        
        Iterator<String[]> iterator = csvData.iterator();
        String[] headers = iterator.next();
 
        ArrayList<Schema> tuples = new ArrayList();
        while (iterator.hasNext()) {
            String[] cellData = iterator.next();
            Schema sc = new Schema();
            sc.setTableName(cellData[TABLE_NAME_INDEX].trim());
            sc.setAttributeName(cellData[ATTRIBUTE_NAME_INDEX].trim());
            tuples.add(sc);
        }
        return tuples;
    }

    public File getSourceFile() {
        return sourceFile;
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
    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }
//    String[] headers;
//    
//    ArrayList<HashMap<String, Object>> readData(int start, int limit) throws FileNotFoundException {
//        CSVReader csvData = new CSVReaderBuilder(new FileReader(sourceFile)).withCSVParser(parser).build();
//        
//        Iterator<String[]> iterator = csvData.iterator();
//        System.out.println("Iterator ready for:"+start+" to "+limit);
//        
//        if(start==0){
//            System.out.println("Getting header");
//            headers = iterator.next();     
//            if(headers[0].trim().equalsIgnoreCase("ï»¿PatientID")) headers[0] = "PatientID";
//            if(headers[0].trim().equalsIgnoreCase("癤풮atientID")) headers[0] = "PatientID";
//        }
//        int rowCount = 0;
//        while(rowCount<start){
//            if(iterator.hasNext()) {
//                iterator.next();
//                rowCount++;
//            }
//            else return null;
//        }
//        System.out.println("Skipped rows:"+rowCount);
//        rowCount = 0;
//        ArrayList<HashMap<String,Object>> tuples = new ArrayList();
//        while(rowCount<limit && iterator.hasNext()){
//            //System.out.println("processing row:"+rowCount);
//            HashMap<String, Object> rowData = new HashMap();
//            
//            String[] cellData = iterator.next();
//            if(headers.length!=cellData.length){
//                System.out.println(headers[0]);
//                System.out.println(cellData[0]);
//                System.out.println("Error! header length("+headers.length+") is not equal to tuple length("+cellData.length+")");
//            }
//            for(int i=0; i<headers.length;i++){
//                rowData.put(headers[i].trim(), cellData[i].trim());
//            }
//            tuples.add(rowData);
//            rowCount++;
//        }
//        return tuples;
//    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    void writeData(ArrayList<String> tuple) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(targetFile));
            writer.writeNext((String[]) tuple.toArray());
        } catch (IOException ex) {
            Logger.getLogger(CsvHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
