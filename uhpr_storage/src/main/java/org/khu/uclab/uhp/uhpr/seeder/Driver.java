package org.khu.uclab.uhp.uhpr.seeder;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.khu.uclab.uhp.uhpr.entities.MedicalFragment;

public class Driver {
	public static void main(String[] args) {
//		SampleUhprGenerator generator = new SampleUhprGenerator();
//		
//		generator.consume();
//		generator.seedMedicalFragments();
//                
            EmrBotsDataConvertor dataHandler = new EmrBotsDataConvertor();
            dataHandler.consume();

//                ObjectMapper mapper = new ObjectMapper();
//
//		try {
//                    
//                    //for(MedicalFragment medFrag: generator.medDataArchive){
//                        // Convert object to JSON string and save into a file directly
//			mapper.writeValue(new File("C:\\Projects\\UHP\\v3_1_data\\lstore.json"),generator.lstore );
//			
//			mapper.writeValue(new File("C:\\Projects\\UHP\\v3_1_data\\uhpr.json"),generator.medDataArchive );
//
//			// Convert object to JSON string and pretty print
//			//String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generator.medDataArchive);
//			//System.out.println(jsonInString);
//                   // }
//			
//
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
                
	}
}
