/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpr.seeder;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private List<String[]> data;
    
    public CsvHandler() {
        targetFile = null;
        data = new ArrayList<>();
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
}