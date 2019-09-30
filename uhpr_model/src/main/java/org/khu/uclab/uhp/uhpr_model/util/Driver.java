package org.khu.uclab.uhp.uhpr_model.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
    
	public static void main(String[] args) {
            final String baseDir = "F:\\Projects\\UHP\\UHP_Results\\v3_5\\";
            try {
                CsvHandler uhprReader = new CsvHandler();
                uhprReader.setTargetMetaFile(new File(baseDir+"uhpr_results_HarryPotter_meta.csv"));
                uhprReader.setTargetDataFile(new File(baseDir+"uhpr_results_HarryPotter.csv"));
                uhprReader.readMetaData();
                uhprReader.readData();
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uhprReader.getUhprmodel());
                System.out.println(jsonInString);
                String outputFileName = uhprReader.getUhprmodel().getName()+"_"+uhprReader.getUhprmodel().getDob();
                mapper.writeValue(new File(baseDir+"uhpr_"+outputFileName+".json"),uhprReader.getUhprmodel() );
                
 
            } catch (IOException ex) {
                Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            }
                
	}
}
