/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap;

import org.khu.uclab.uhp.uhpmap.model.SchemaBridge;
import org.khu.uclab.uhp.uhpmap.model.Schema;
import org.khu.uclab.uhp.uhpmap.util.CsvHandler;
import com.googlecode.concurrenttrees.common.CharSequences;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharSequenceNodeFactory;
import com.googlecode.concurrenttrees.solver.LCSubstringSolver;
import org.khu.uclab.uhp.uhpmap.conceptnet.ConceptNetWrapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.khu.uclab.uhp.uhpmap.conceptnet.Edge;
import org.khu.uclab.uhp.uhpmap.conceptnet.Relation;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Matcher {

    String readPath = "F:\\Projects\\UHP\\schema matching\\fahad\\";
    String writePath = "F:\\Projects\\UHP\\schema matching\\fahad\\processed";
    ArrayList<ArrayList<String>> schemaProcessed = new ArrayList();
    BufferedWriter fw1 = null;
    ConceptNetWrapper conceptNetHandler = new ConceptNetWrapper();
    
    // Use HashSet for fast lookup and avoiding duplicate objects
    HashSet<SchemaBridge> UHP_MAP = new HashSet();
    HashMap<String, HashMap<Relation,Edge>> conceptNetMap = new HashMap();
    HashMap<String, ArrayList<Edge>> conceptNetMap2 = new HashMap();
    
    //This word has nothing on concept net so skip it
    HashSet<String> conceptNetBlackList = new HashSet();
    
    
    public static void main(String args[]) {
        Matcher semMatch = new Matcher();
        semMatch.process();
    }

    public void process() {
        try {
            try {
                CsvHandler schemaReader = new CsvHandler();
                
                schemaReader.setSourceFile(new File(readPath + "schemas.csv"));
                
                ArrayList<Schema> schemaList = schemaReader.readData();
                System.out.println("Original Schema Size:"+schemaList.size());
                
                for (int i = 0; i < schemaList.size(); i++) {
                    String attrLeft = schemaList.get(i).getAttributeName().toLowerCase();
                    boolean checkFurther=true;
                    //Simple lowercase string match
                    for (int j = 0; j < schemaList.size(); j++) {
                        boolean sameTable = schemaList.get(i).getTableName().equals(schemaList.get(j).getTableName());
                        if(i==j) continue;
                        if(sameTable) continue;
                        
                        //System.out.println(schemaList.get(i).getTableName()+"-"+schemaList.get(j).getTableName()+":"+sameTable);
                        
                        attrLeft = attrLeft.replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
                        String attrRight = schemaList.get(j).getAttributeName().toLowerCase().replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
                        
                        if (attrLeft.equals(attrRight)) {
                            System.out.println(attrLeft+" == "+attrRight);
                            UHP_MAP.add(new SchemaBridge(schemaList.get(i), schemaList.get(j), SchemaRelation.EQUAL, "equalsIgnoreCase"));
                            schemaList.remove(i);
                            schemaList.remove(j);
                            checkFurther = false;
                        }
                    }
                    if(!checkFurther) continue;
                    System.out.println("UHP_MAP size after equality check:"+UHP_MAP.size());
                    //Suffix Tree based longest sequence match
                    for (int j = 0; j < schemaList.size(); j++) {
                        String attrRight = schemaList.get(j).getAttributeName().toLowerCase();
                        //System.out.println(schemaList.get(i).getTableName()+" == "+schemaList.get(j).getTableName());
                        boolean sameTable = schemaList.get(i).getTableName().equals(schemaList.get(j).getTableName());
                        if (i != j && !sameTable) {
                            LCSubstringSolver solver = new LCSubstringSolver(new DefaultCharSequenceNodeFactory());
                            solver.add(attrLeft);
                            solver.add(attrRight);
                            String longestCommonSubstring = CharSequences.toString(solver.getLongestCommonSubstring());
                            if (longestCommonSubstring.length() > 1) {
                                double threshold1 = (attrLeft.length()/2)*3;
                                double threshold2 = (attrRight.length()/2)*3;
                                if(longestCommonSubstring.length()>threshold1 && longestCommonSubstring.length()>threshold2){
                                    SchemaBridge sc = new SchemaBridge(schemaList.get(i), schemaList.get(j), SchemaRelation.NOTEQUAL, "SuffixTreeFullAttribute");
                                    System.out.println(sc);
                                    UHP_MAP.add(sc);
                                }
                                //System.out.println(schemaList.get(i).getAttributeName()+" == "+schemaList.get(j).getAttributeName()+":"+longestCommonSubstring);
                            }
                        }
                    }
                    
                    //Suffix Tree based longest sequence match for each word
                    for (int j = 0; j < schemaList.size(); j++) {
                        //System.out.println(schemaList.get(i)+" == "+schemaList.get(j));
                        boolean sameTable = schemaList.get(i).getTableName().equals(schemaList.get(j).getTableName());
                        String attrRight = schemaList.get(j).getAttributeName();
                        if (i != j && !sameTable) {
                            //System.out.println(schemaList.get(j).getAttributeName());
                            String regex = "(?<=.)(?=\\p{Lu})|(?<=.)(?=\\d)|(?<=.)(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
                            String[] words = attrRight.split(regex);
                            boolean[] matchStatus = new boolean[words.length];
                            String[] matchString = new String[words.length];
                            for (int k = 0; k < words.length; k++) {
                                if (words[k].length() > 1) {
                                    
                                    LCSubstringSolver solver = new LCSubstringSolver(new DefaultCharSequenceNodeFactory());
                                    solver.add(attrLeft);
                                    solver.add(words[k].toLowerCase());
                                    String longestCommonSubstring = CharSequences.toString(solver.getLongestCommonSubstring());
                                    if (longestCommonSubstring.length() > 1) {
                                        double threshold = (words[k].length() / 3) * 2;
                                        //System.out.println(longestCommonSubstring.length()+"----" + threshold);
                                        matchStatus[k] = longestCommonSubstring.length() > threshold;
                                        matchString[k] = longestCommonSubstring;
                                    }
                                }
                            }
//                        System.out.println(schemaList.get(i).getAttributeName());
//                        System.out.println(Arrays.toString(words));
//                        System.out.println(Arrays.toString(matchStatus));
//Check if all the words have passed 2/3 check
boolean finalCheck = false;
for (boolean status : matchStatus) {
    if (!status) {
        finalCheck = false;
        break;
    } else {
        finalCheck = true;
    }
}
if (finalCheck) {
    // This is what we need to store
//                                SchemaBridge sb = new SchemaBridge(schemaList.get(i),schemaList.get(j),SchemaRelation.NOTEQUAL,"SuffixTreeParts");
//                                        sb.setComments(Arrays.toString(matchString));
//
// But we check this to see if it already exists, because if it does ten we should change its status
SchemaBridge sb = new SchemaBridge(schemaList.get(i), schemaList.get(j), SchemaRelation.NOTEQUAL, "SuffixTreeParts");
sb.setComments(Arrays.toString(matchString));
//boolean newAdd = true;
// Iterating over hash set items
//System.out.println("Iterating over list:");
//Iterator<SchemaBridge> uhp_i = UHP_MAP.iterator();
//                            while(uhp_i.hasNext()){
//                                SchemaBridge sc = uhp_i.next();
//                                if (sc.equals(sb)) {
//                                    System.out.println(sc + "="+sb);
//                                    sc.setRelationship(SchemaRelation.EQUAL);
//                                    sc.setComments(sb.getComments());
//                                    newAdd = false;
//                                }
//                            }

//if (!newAdd) {
System.out.println("Adding a new element after partial word check");
UHP_MAP.add(sb);
//}

}

                        }
                    }
                    
                    //ConceptNet match for each word
                    for (int j = 0; j < schemaList.size(); j++) {
                        boolean sameTable = schemaList.get(i).getTableName().equals(schemaList.get(j).getTableName());
                        String attrRight = schemaList.get(j).getAttributeName();
                        if (i != j && !sameTable) {
                            String regex = "(?<=.)(?=\\p{Lu})|(?<=.)(?=\\d)|(?<=.)(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
                            String[] wordsLeft = attrLeft.split(regex);
                            String[] wordsRight = attrRight.split(regex);
                            
                            
                            // Build the ConceptNetMap
                            
                            //Store status and string of each word in the left string with all corresponding words of the right string
                            int[][] matchStatus = new int[wordsLeft.length][wordsRight.length];
                            String[][] matchString = new String[wordsLeft.length][wordsRight.length];
                            
                            ArrayList<Edge> relatedness = new ArrayList();
                            for (int leftIte = 0; leftIte<wordsLeft.length;leftIte++){
                                String wordsLeft1 = wordsLeft[leftIte];
                                wordsLeft1 = wordsLeft1.replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
                                if(wordsLeft1.length()<2){
                                    continue;
                                }
                                if(conceptNetBlackList.contains(wordsLeft1)) continue;
                                System.out.println(Arrays.toString(wordsLeft));
                                System.out.println(wordsLeft1);
                                for (String wordsRight1 : wordsRight) {
                                    if(wordsRight1.length()<2){
                                        continue;
                                    }
                                    if(conceptNetBlackList.contains(wordsRight1)) continue;
                                    String keyL = wordsLeft1+"-"+wordsRight1;
                                    String keyR = wordsRight1+"-"+wordsLeft1;
                                    if (conceptNetMap2.containsKey(keyL)) {
                                        relatedness = conceptNetMap2.get(keyL);
                                    }else{
                                        ArrayList<Edge> results = conceptNetHandler.queryRelatedTo(wordsLeft1,wordsRight1);
                                        if(results==null || results.size()<1){
                                            HashMap <Relation,Edge> LeftResult = conceptNetHandler.query(wordsLeft1);
                                            if(LeftResult.size()>0) continue;  //The right word was problematic so just skip this one.
                                            else {
                                                conceptNetBlackList.add(wordsLeft1);
                                                System.out.println("breaking");
                                                break;
                                            }       //No need to look up this word anymore we will not find any results
                                            
                                        }
                                        conceptNetMap2.put(keyL, results);
                                        conceptNetMap2.put(keyR, results);
                                        relatedness = conceptNetMap2.get(keyL);
                                    }
                                    if(relatedness!=null && relatedness.size()>0){
                                        relatedness.forEach(edge->{System.out.println(edge);});

                                    }

                                }
                                //System.out.println("breaking2");
                            }
                            
                            
                            
                            
                            //Now check each word for its mapping
//                        for(int wordsLeftIte=0;wordsLeftIte<wordsLeft.length;wordsLeftIte++) {
//                            String wordL = wordsLeft[wordsLeftIte];
//                            HashMap<Relation, Edge> leftConcept;
//                            if(conceptNetMap.containsKey(wordL)) {
//                                leftConcept = conceptNetMap.get(wordL);
//                            }else{
//                                conceptNetMap.put(wordL, conceptNetHandler.query(wordL));
//                                leftConcept = conceptNetMap.get(wordL);
//                            }
//                            if(leftConcept!=null && leftConcept.size()>0){
//                                for (int wordsRightIte=0;wordsRightIte<wordsRight.length;wordsRightIte++) {
//                                    String wordR = wordsRight[wordsRightIte];
//                                    HashMap<Relation, Edge> rightConcept;
//                                    if(conceptNetMap.containsKey(wordR)) {
//                                        rightConcept = conceptNetMap.get(wordR);
//                                    }else{
//                                        conceptNetMap.put(wordR, conceptNetHandler.query(wordR));
//                                        rightConcept = conceptNetMap.get(wordR);
//                                    }
//                                    if(rightConcept!=null && rightConcept.size()>0){
//                                        
//                                        // Check the similarity of concepts here
////                                        System.out.println(leftConcept);
////                                        System.out.println(rightConcept);
////                                        boolean similarCheck;
////                                        boolean isACheck;
////                                        boolean hasACheck;
////                                        boolean derivedFromCheck;
////                                        try{
////                                            similarCheck = leftConcept.get(Relation.SimilarTo).equals(rightConcept.get(Relation.SimilarTo));
////                                        }catch(NullPointerException npe){
////                                            similarCheck = false;
////                                        }
////                                        try{
////                                            isACheck = leftConcept.get(Relation.IsA).equals(rightConcept.get(Relation.IsA));
////                                        }catch(NullPointerException npe){
////                                            isACheck = false;
////                                        }
////                                        try{
////                                            hasACheck = leftConcept.get(Relation.HasA).equals(rightConcept.get(Relation.HasA));
////                                        }catch(NullPointerException npe){
////                                            hasACheck = false;
////                                        }
////                                        try{
////                                            derivedFromCheck = leftConcept.get(Relation.DerivedFrom).equals(rightConcept.get(Relation.DerivedFrom));
////                                        }catch(NullPointerException npe){
////                                            derivedFromCheck = false;
////                                        }
////                                        
////                                        if(similarCheck || isACheck || hasACheck || derivedFromCheck){
////                                            System.out.println("|SimilarCheck:"+similarCheck+"|isACheck:"+isACheck+"|hasACheck:"+hasACheck+"|derivedFromCheck:"+derivedFromCheck+"|");
////                                            matchStatus[wordsLeftIte][wordsRightIte] = 1;
////                                            matchString[wordsLeftIte][wordsRightIte] = "|-|SimilarCheck:"+similarCheck+"|isACheck:"+isACheck+"|hasACheck:"+hasACheck+"|derivedFromCheck:"+derivedFromCheck+"|-|";
////                                        
////                                        }else{
////                                            matchStatus[wordsLeftIte][wordsRightIte] = 0;
////                                        }
//                                    }
//                                }
//                            }
//                        }
//                        
//                        //Check if all words on the left have atleast 2/3 matching words on the right
//                        boolean finalCheck = false;
//                        for (int[] statusLeft : matchStatus) {
//                            int totalMatches = 0;
//                            for (int statusRight : statusLeft) {
//                                totalMatches += statusRight;
//                            }
//                            if(totalMatches>(wordsLeft.length/2)*3){
//                                finalCheck = true;
//                            }
//                        }
//                        if (finalCheck) {
//                            // This is what we need to store
//                            SchemaBridge sb = new SchemaBridge(schemaList.get(i), schemaList.get(j), SchemaRelation.NOTEQUAL, "ConceptNetParts");
//                            sb.setComments(Arrays.deepToString(matchString));
//                            System.out.println("Adding a new element after ConceptNet check");
//                            UHP_MAP.add(sb);
//                        }

                        }
                    }
                    
                }
                
                Iterator hashSetIterator = UHP_MAP.iterator();
                while(hashSetIterator.hasNext()){
                    System.out.println(hashSetIterator.next());
                }
                System.out.println("UHP_MAP Size:"+UHP_MAP.size());
                //System.out.println(UHP_MAP);
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }

            File file = new File(writePath);
            file.mkdirs();
            String realpath = writePath + "\\" + "schema_processed" + System.currentTimeMillis() + ".csv";
            fw1 = new BufferedWriter(new FileWriter(realpath, true));
            UHP_MAP.forEach((tuple)-> {
                try {
                    fw1.write(tuple.toString());
                    fw1.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fw1.close();
        } catch (IOException ex) {
            Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
