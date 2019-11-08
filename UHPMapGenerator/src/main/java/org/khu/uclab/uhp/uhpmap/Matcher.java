/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.khu.uclab.uhp.uhpmap.conceptnet.Edge;
import org.khu.uclab.uhp.uhpmap.conceptnet.RelatednessNode;
import org.khu.uclab.uhp.uhpmap.conceptnet.Relation;
import org.khu.uclab.uhp.uhpmap.snomedct.SnomedCtWrapper;
import org.khu.uclab.uhp.uhpmap.snomedct.rf2.MatchResults;

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
    SnomedCtWrapper snomedHandler = new SnomedCtWrapper();

    // Use HashSet for fast lookup and avoiding duplicate objects
    HashSet<AttributeMap> UHP_MAP = new HashSet();
    HashMap<String, HashMap<Relation, Edge>> conceptNetMap = new HashMap();
    HashMap<String, ArrayList<Edge>> conceptNetMap2 = new HashMap();

    //This word has nothing on concept net so skip it
    HashSet<String> conceptNetBlackList = new HashSet();

    //This word has nothing on SNOMED so skip it
    HashSet<String> snomedNetBlackList = new HashSet();

    boolean _isConceptNetEnabled = true;
    boolean _isSnomedEnabled = false;

    public static void main(String args[]) {
        Matcher semMatch = new Matcher();
        semMatch.process();
    }
    public boolean isSameSchema(String TableLeft, String TableRight){
        String schemaLeftName = TableLeft.substring(0, TableLeft.indexOf("_"));
        String schemaRightName = TableRight.substring(0, TableRight.indexOf("_"));
        return schemaLeftName.equalsIgnoreCase(schemaRightName);
    }
    public void process() {
        try {
            try {
                CsvHandler schemaReader = new CsvHandler();

                schemaReader.setSourceFile(new File(readPath + "schemas.csv"));

                ArrayList<Schema> schemaList = schemaReader.readData();
                System.out.println("Original Schema Size:" + schemaList.size());

                for (int i = 0; i < schemaList.size(); i++) {
                    String attrLeft = schemaList.get(i).getAttributeName();
                    boolean checkFurther = true;
                    //Simple lowercase string match
                    for (int j = 0; j < schemaList.size(); j++) {
                        if (i == j) {
                            continue;
                        }
                        if (isSameSchema(schemaList.get(i).getTableName(),schemaList.get(j).getTableName())) {
                            continue;
                        }
                        
                        attrLeft = attrLeft.replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
                        String attrRight = schemaList.get(j).getAttributeName().replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");

                        if (attrLeft.equalsIgnoreCase(attrRight)) {
                            System.out.println(attrLeft + " == " + attrRight);
                            UHP_MAP.add(new AttributeMap(schemaList.get(i), schemaList.get(j), SchemaRelation.EQUAL, "equalsIgnoreCase",1.0));
                            //UHP_MAP.add(new AttributeMap(schemaList.get(j), schemaList.get(i), SchemaRelation.EQUAL, "equalsIgnoreCase",1.0));
//                            schemaList.remove(i);
//                            schemaList.remove(j);
                            checkFurther = false;
                        }
                    }
                    if (!checkFurther) {
                        continue;
                    }
                    System.out.println("UHP_MAP size after equality check:" + UHP_MAP.size());
                    //Suffix Tree based longest sequence match
                    for (int j = 0; j < schemaList.size(); j++) {
                        String attrRight = schemaList.get(j).getAttributeName();
                        //System.out.println(schemaList.get(i).getTableName()+" == "+schemaList.get(j).getTableName());
                        boolean sameSchema = isSameSchema(schemaList.get(i).getTableName(),schemaList.get(j).getTableName());
                        if (i != j && !sameSchema) {
                            LCSubstringSolver solver = new LCSubstringSolver(new DefaultCharSequenceNodeFactory());
                            solver.add(attrLeft);
                            solver.add(attrRight);
                            
                            String longestCommonSubstring = CharSequences.toString(solver.getLongestCommonSubstring());
                            if (longestCommonSubstring.length() > 1) {
                                double threshold1 = (attrLeft.length() / 2) * 3;
                                //double threshold2 = (attrRight.length() / 2) * 3;
                                if (longestCommonSubstring.length() > threshold1){
                                    double confidence = 1 - ((attrLeft.length() - longestCommonSubstring.length())/attrLeft.length());
                                        AttributeMap sc = new AttributeMap(
                                                schemaList.get(i), schemaList.get(j),
                                                SchemaRelation.SUBSUMPTION, "SuffixTreeFullAttribute",confidence);
                                        sc.setComments(longestCommonSubstring);
                                        //System.out.println(sc);
                                        UHP_MAP.add(sc);
                                
                                }
//                                if(longestCommonSubstring.length() > threshold2) {
//                                    double confidence = 1 - ((attrRight.length() - longestCommonSubstring.length())/attrRight.length());
//                                        AttributeMap sc = new AttributeMap(
//                                                schemaList.get(j), schemaList.get(i),
//                                                SchemaRelation.SUBSUMPTION, "SuffixTreeFullAttribute",confidence);
//                                        sc.setComments(longestCommonSubstring);
//                                        //System.out.println(sc);
//                                        UHP_MAP.add(sc);
//                                
//                                }
                                //System.out.println(schemaList.get(i).getAttributeName()+" == "+schemaList.get(j).getAttributeName()+":"+longestCommonSubstring);
                            }
                        }
                    }

                    //Suffix Tree based longest sequence match for each word
                    for (int j = 0; j < schemaList.size(); j++) {
                        //System.out.println(schemaList.get(i)+" == "+schemaList.get(j));
                        String attrRight = schemaList.get(j).getAttributeName();
                        if (i != j && !isSameSchema(schemaList.get(i).getTableName(),schemaList.get(j).getTableName())) {
                            //System.out.println(schemaList.get(j).getAttributeName());
                            String regex = "(?<=.)(?=\\p{Lu})|(?<=.)(?=\\d)|(?<=.)(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
                            String[] words = attrRight.split(regex);
                            boolean[] matchStatus = new boolean[words.length];
                            String[] matchString = new String[words.length];
                            for (int k = 0; k < words.length; k++) {
                                if (words[k].length() > 1) {

                                    LCSubstringSolver solver = new LCSubstringSolver(new DefaultCharSequenceNodeFactory());
                                    solver.add(attrLeft);
                                    solver.add(words[k]);
                                    String longestCommonSubstring = CharSequences.toString(solver.getLongestCommonSubstring());
                                    //double threshold2 = (attrRight.length() / 2) * 3;
                                    double threshold2 = 1;
                                    if (longestCommonSubstring.length() > threshold2) {
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
//                                AttributeMap sb = new AttributeMap(schemaList.get(i),schemaList.get(j),SchemaRelation.NOTEQUAL,"SuffixTreeParts");
//                                        sb.setComments(Arrays.toString(matchString));
//
                                // But we check this to see if it already exists, because if it does then we should change its status
                                AttributeMap sb = new AttributeMap(schemaList.get(j), schemaList.get(i), SchemaRelation.SUBSUMPTION, "SuffixTreeParts",0.6);
                                sb.setComments(Arrays.toString(matchString));

                                //System.out.println("Adding a new element after partial word check");
                                UHP_MAP.add(sb);
//}

                            }

                        }
                    }
                    System.out.println("UHP_MAP size after Suffix Tree Parts check:" + UHP_MAP.size());
                    if (_isConceptNetEnabled) {
                        System.out.println("################ CONCEPT NET #####################");
                        //ConceptNet match for each word
                        for (int j = 0; j < schemaList.size(); j++) {
                             String attrRight = schemaList.get(j).getAttributeName();
                            if (i != j && !isSameSchema(schemaList.get(i).getTableName(),schemaList.get(j).getTableName())) {
                                String regex = "(?<=.)(?=\\p{Lu})|(?<=.)(?=\\d)|(?<=.)(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
                                String[] wordsLeft = attrLeft.split(regex);
                                String[] wordsRight = attrRight.split(regex);

                                // Build the ConceptNetMap
                                //Store status and string of each word in the left string with all corresponding words of the right string
                                WordMap[][] wordsMap = new WordMap[wordsLeft.length][wordsRight.length];
//                                String[][] matchString = new String[wordsLeft.length][wordsRight.length];
//                                Double[][] matchConfidence = new Double[wordsLeft.length][wordsRight.length];
//                                String[][] matchComments = new String[wordsLeft.length][wordsRight.length];
                                boolean finalMatchTest = true;
                                double totalWeight=0;
                                int countRelation = 0;
                                //ArrayList<Edge> relatedness;
                                for (int wIte1 = 0; wIte1<wordsLeft.length; wIte1++){
                                    String wordsLeft1 = wordsLeft[wIte1];
                                    wordsLeft1 = wordsLeft1.replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
                                    if (wordsLeft1.length() < 2) {
                                        continue;
                                    }

                                    if (conceptNetBlackList.contains(wordsLeft1)) {
                                        continue;
                                    }
                                    //System.out.println(Arrays.toString(wordsLeft));
                                    //System.out.println(wordsLeft1);
                                    for (int wIte2 = 0; wIte2<wordsRight.length; wIte2++){
                                        String wordsRight1 = wordsRight[wIte2];
                                        wordsRight1 = wordsRight1.replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
                                        if (wordsRight1.length() < 2) {
                                            continue;
                                        }
                                        if (conceptNetBlackList.contains(wordsRight1)) {
                                            continue;
                                        }
                                        
                                       RelatednessNode relatedness = conceptNetHandler.queryRelatedness(wordsLeft1, wordsRight1);
                                        //System.out.println(relatedness);
                                        if(relatedness!=null && relatedness.getWeight()>0){
                                            //ArrayList<RelatednessNode> relatednessArray = conceptNetHandler.queryRelatedTerms(wordsLeft1, wordsRight1);
                                            
//                                            if(relatednessArray.size()>0){
//                                                for( int relatedNodeIte = 0; relatedNodeIte<relatednessArray.size();relatedNodeIte++){
//                                                    RelatednessNode relatedNode = relatednessArray.get(relatedNodeIte);
//                                                    if(relatedNode.getWeight()>0.9){
//                                                        AttributeMap sb = new AttributeMap(schemaList.get(i),
//                                                            schemaList.get(j), SchemaRelation.EQUAL, "ConceptNet",relatedNode.getWeight());
//                                                        sb.setComments(relatedNode.toString());
//                                                        UHP_MAP.add(sb);
//                                                    }
//                                                    else if(relatedNode.getWeight()>0){
//                                                        AttributeMap sb = new AttributeMap(schemaList.get(i),
//                                                            schemaList.get(j), SchemaRelation.AMBIGIOUS, "ConceptNet",relatedNode.getWeight());
//                                                        sb.setComments(relatedNode.toString());
//                                                        UHP_MAP.add(sb);
//                                                    }
//                                                }
//                                            }else{
                                                //Something very wrong happened, due to which event though we have a relatedness value
                                                // there are no related terms. Perhaps, server was too busy? 
                                                if(relatedness.getWeight()>0.9){
                                                    
                                                    wordsMap[wIte1][wIte2] = new WordMap(wordsLeft1,wordsRight1,SchemaRelation.EQUAL,"ConceptNet",relatedness.getWeight());
                                                    wordsMap[wIte1][wIte2].setComments(relatedness.toString());
//                                                    
//                                                    wordsBridge = new AttributeMap(
//                                                    matchStatus[wIte1][wIte2] = true;
//                                                    matchString[wIte1][wIte2] = wordsLeft1+"-"+SchemaRelation.EQUAL.getLabel()+"-"+wordsRight1;
//                                                    matchConfidence[wIte1][wIte2] = relatedness.getWeight();
//                                                    matchComments[wIte1][wIte2] = relatedness.toString();
                                                }else if(relatedness.getWeight()>0){
                                                    wordsMap[wIte1][wIte2] = new WordMap(wordsLeft1,wordsRight1,SchemaRelation.AMBIGIOUS,"ConceptNet",relatedness.getWeight());
                                                    wordsMap[wIte1][wIte2].setComments(relatedness.toString());
                                                }
                                                
                                                finalMatchTest = finalMatchTest & true;
                                                totalWeight += relatedness.getWeight();
                                                countRelation++;
//                                                    AttributeMap sb = new AttributeMap(schemaList.get(i),
//                                                            schemaList.get(j), SchemaRelation.EQUAL, "ConceptNet",relatedness.getWeight());
//                                                        sb.setComments(relatedness.toString());
//                                                        UHP_MAP.add(sb);
//                                                    }
//                                                    else if(relatedness.getWeight()>0){
//                                                        AttributeMap sb = new AttributeMap(schemaList.get(i),
//                                                            schemaList.get(j), SchemaRelation.AMBIGIOUS, "ConceptNet",relatedness.getWeight());
//                                                        sb.setComments(relatedness.toString());
//                                                        UHP_MAP.add(sb);
//                                                    }
//                                                AttributeMap sb = new AttributeMap(schemaList.get(i),
//                                                                schemaList.get(j), SchemaRelation.AMBIGIOUS, "ConceptNet",relatedness.getWeight());
//                                                            sb.setComments(relatedness.toString());
//                                                        UHP_MAP.add(sb);
                                        }else{
                                            //conceptNetHandler.
                                                finalMatchTest = finalMatchTest & false;
                                        }
                                        

                                    }
                                    //System.out.println("breaking2");
                                }
                                //Save concept net result here.
                                if(countRelation==0) continue;
                                
                                double averageWeight = totalWeight / countRelation;
                                
                                AttributeMap sb;
                                
                                if(averageWeight>0.9){
                                    sb= new AttributeMap(schemaList.get(i), schemaList.get(j), SchemaRelation.EQUAL, "ConceptNet",averageWeight);
                                }else{
                                    sb= new AttributeMap(schemaList.get(i), schemaList.get(j), SchemaRelation.AMBIGIOUS, "ConceptNet",averageWeight);
                                }
                                sb.setComments(Arrays.deepToString(wordsMap));
                                UHP_MAP.add(sb);
                            }
                        }//CONCEPT NET Iterating Schema List for Right hand terms
                    }

                    if (this._isSnomedEnabled) {
                        System.out.println("################ SNOMED #####################");
                        //Identify the SNOMED Concepts
                        for (int j = 0; j < schemaList.size(); j++) {
                            String attrRight = schemaList.get(j).getAttributeName();
                            if (i != j && !isSameSchema(schemaList.get(i).getTableName(),schemaList.get(j).getTableName())) {

                                MatchResults resultLeft = snomedHandler.query(attrLeft);
                                MatchResults resultRight = snomedHandler.query(attrRight);

                                System.out.println(resultLeft.equals(resultRight));
//                            String regex = "(?<=.)(?=\\p{Lu})|(?<=.)(?=\\d)|(?<=.)(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
//                            String[] wordsLeft = attrLeft.split(regex);
//                            String[] wordsRight = attrRight.split(regex);
//
//                            // Build the SNOMEDMap
//                            //Store status and string of each word in the left string with all corresponding words of the right string
//                            int[][] matchStatus = new int[wordsLeft.length][wordsRight.length];
//                            String[][] matchString = new String[wordsLeft.length][wordsRight.length];
//
//                            ArrayList<Edge> relatedness = new ArrayList();
//                            for (int leftIte = 0; leftIte < wordsLeft.length; leftIte++) {
//                                String wordsLeft1 = wordsLeft[leftIte];
//                                wordsLeft1 = wordsLeft1.replaceAll("(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])", "");
//                                if (wordsLeft1.length() < 2) {
//                                    continue;
//                                }
//
//                                if (snomedNetBlackList.contains(wordsLeft1)) {
//                                    continue;
//                                }
//                                System.out.println(Arrays.toString(wordsLeft));
//                                System.out.println(wordsLeft1);
//                                for (String wordsRight1 : wordsRight) {
//                                    if (wordsRight1.length() < 2) {
//                                        continue;
//                                    }
//                                    if (snomedNetBlackList.contains(wordsRight1)) {
//                                        continue;
//                                    }
//                                    String keyL = wordsLeft1 + "-" + wordsRight1;
//                                    String keyR = wordsRight1 + "-" + wordsLeft1;
//                                    if (conceptNetMap2.containsKey(keyL)) {
//                                        relatedness = conceptNetMap2.get(keyL);
//                                    } else {
//                                        MatchResults resultLeft = snomedHandler.query(wordsLeft1);
//                                        MatchResults resultRight = snomedHandler.query(wordsRight1);
//                                        if (results == null || results.size() < 1) {
//                                            HashMap<Relation, Edge> LeftResult = snomedHandler.query(wordsLeft1);
//                                            if (LeftResult.size() > 0) {
//                                                continue;  //The right word was problematic so just skip this one.
//                                            } else {
//                                                snomedNetBlackList.add(wordsLeft1);
//                                                System.out.println("breaking");
//                                                break;
//                                            }       //No need to look up this word anymore we will not find any results
//
//                                        }
//                                        snomedMap2.put(keyL, results);
//                                        snomedMap2.put(keyR, results);
//                                        relatedness = snomedMap2.get(keyL);
//                                    }
//                                    if (relatedness != null && relatedness.size() > 0) {
//                                        relatedness.forEach(edge -> {
//                                            System.out.println(edge);
//                                        });
//
//                                    }
//
//                                }
//                                //System.out.println("breaking2");
//                            }

                            }
                        }//SNOMED Iterating Schema List for Right hand terms
                    }

                }//Iterating Schema List for Left hand terms

                Iterator hashSetIterator = UHP_MAP.iterator();
                while (hashSetIterator.hasNext()) {
                    System.out.println(hashSetIterator.next());
                }
                System.out.println("UHP_MAP Size:" + UHP_MAP.size());
                //System.out.println(UHP_MAP);
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }

            File file = new File(writePath);
            file.mkdirs();
            String realpath = writePath + "\\" + "schema_processed" + System.currentTimeMillis() + ".json";
            ObjectMapper UhpMapJson = new ObjectMapper();
            ObjectWriter UhpMapWriter = UhpMapJson.writer(new DefaultPrettyPrinter());
            UhpMapWriter.writeValue(new File(realpath),UHP_MAP);
            
            
            //Write UHP_MAP as csv
//            fw1 = new BufferedWriter(new FileWriter(realpath, true));
//            UHP_MAP.forEach((tuple) -> {
//                try {
//                    fw1.write(tuple.toString());
//                    fw1.write('\n');
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            fw1.close();
        } catch (IOException ex) {
            Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
