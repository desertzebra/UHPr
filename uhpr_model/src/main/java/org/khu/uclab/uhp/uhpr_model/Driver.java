package org.khu.uclab.uhp.uhpr_model;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.khu.uclab.uhp.uhpr_model.entities.UhprModel;
import org.khu.uclab.uhp.uhpr_model.uhpmap.model.AttributeMap;

public class Driver {

    private final String schemaMapReadPath = "F:\\Projects\\UHP\\schema matching\\fahad\\processed\\schema_processed1586418031860.json";
    private final String metadataFile = "F:\\Projects\\UHP\\UHP_quantitative_Results\\v3_5\\uhpr_results_HarryPotter_meta.csv";
    private final String dataFile = "F:\\Projects\\UHP\\UHP_quantitative_Results\\v3_5\\uhpr_results_HarryPotter.csv";
    private final String outputDir = "F:\\Projects\\UHP\\UHP_Processed_Results\\UHPrModel";
    private UhprModel outputUhprModel = null;
    private HashSet<AttributeMap> schemaMap = null;
    private final int mappingMode = 1;            //set 1 for semantic linking and 2 for transformation
    public void process() {

        // create an empty Uhpr Model
        outputUhprModel = new UhprModel();
        // create a Json reader to read the Schema Map
        try{
            ObjectMapper UhpMapJsonMapper = new ObjectMapper(); 
            schemaMap = UhpMapJsonMapper.readValue(new File(schemaMapReadPath), HashSet.class);
            System.out.println("****************Schema Map****************");
            System.out.println(schemaMap);
        } catch (IOException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            schemaMap = null;
        }
        
        
        // create a CSV reader to read the data
        try {
            CsvHandler uhprReader = new CsvHandler(outputUhprModel);
            uhprReader.setTargetMetaFile(new File(metadataFile));
            uhprReader.setTargetDataFile(new File(dataFile));
            uhprReader.readMetaData();
            uhprReader.readData();
        } catch (IOException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            outputUhprModel = null;
        }
        
        // Apply the UHP Map
        if(mappingMode==1){
            
        }else{
            
        }
        
        if (outputUhprModel != null) {
            try {
                // Write the UHPr model into output file
                File file = new File(outputDir);
                String outputFileName = outputUhprModel.getName() + "_" + outputUhprModel.getDob();
                file.mkdirs();
                String realpath = outputDir + "\\" + "uhpr_" + outputFileName + "_" + System.currentTimeMillis() + ".json";

                ObjectMapper UhprModelJsonMapper = new ObjectMapper();
                ObjectWriter UhprModelWriter = UhprModelJsonMapper.writer(new DefaultPrettyPrinter());
                UhprModelWriter.writeValue(new File(realpath), outputUhprModel);
            } catch (IOException ex) {
                Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    
    // start execution. This should receive args for chaning behaviour.
    public static void main(String[] args) {
        Driver UhprModelBuilder = new Driver();
        UhprModelBuilder.process();
    }
}
