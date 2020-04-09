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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.khu.uclab.uhp.uhpmap.comparator.ConceptNetThreadedComparator;
import org.khu.uclab.uhp.uhpmap.comparator.UhpMapUtils;
import org.khu.uclab.uhp.uhpmap.model.AttributeMap;
import org.khu.uclab.uhp.uhpmap.model.SchemaRelation;
import org.khu.uclab.uhp.uhpmap.conceptnet.Edge;
import org.khu.uclab.uhp.uhpmap.conceptnet.RelatednessNode;
import org.khu.uclab.uhp.uhpmap.conceptnet.Relation;
import org.khu.uclab.uhp.uhpmap.model.Attribute;
import org.khu.uclab.uhp.uhpmap.model.Concept;
import org.khu.uclab.uhp.uhpmap.model.Word;
import org.khu.uclab.uhp.uhpmap.model.WordMap;
import org.khu.uclab.uhp.uhpmap.snomedct.SnomedCtWrapper;
import org.khu.uclab.uhp.uhpmap.umls.UmlsWrapper;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class Matcher {

    private final String readPath = "F:\\Projects\\UHP\\schema matching\\fahad\\";
    private final String writePath = "F:\\Projects\\UHP\\schema matching\\fahad\\processed";
    ArrayList<ArrayList<String>> schemaProcessed = new ArrayList();
    BufferedWriter fw1 = null;
    ConceptNetWrapper conceptNetHandler = new ConceptNetWrapper();
    SnomedCtWrapper snomedHandler = new SnomedCtWrapper();
    UmlsWrapper umlsHandler = new UmlsWrapper();

    // Use HashSet for fast lookup and avoiding duplicate objects
    HashSet<AttributeMap> UHP_MAP = new HashSet();
    HashMap<String, HashMap<Relation, Edge>> conceptNetMap = new HashMap();
    HashMap<String, String> snomedCtMap = new HashMap();
    HashMap<String, List<Concept>> umlsMap = new HashMap();
    HashMap<String, ArrayList<Edge>> conceptNetMap2 = new HashMap();

    //This word has nothing on concept net so skip it
    HashSet<String> conceptNetBlackList = new HashSet();

    //This word has nothing on SNOMED so skip it
    HashSet<String> snomedCtBlackList = new HashSet();

    //This word has nothing on UMLS so skip it
    HashSet<String> umlsBlackList = new HashSet();

    private final boolean _isConceptNetEnabled = true;
    private final boolean _isSnomedEnabled = false;
    private final boolean _isUmlsEnabled = true;

    //String ALLACRONYM_URL = "https://www.allacronyms.com";
    //String SNOMED_CT_URL = "https://browser.ihtsdotools.org/snowstorm/snomed-ct/browser/MAIN/2020-03-09/descriptions?&limit=100&active=true&conceptActive=true&lang=english";
    public static void main(String args[]) {
        Matcher semMatch = new Matcher();
        semMatch.process();
        System.out.println("Exiting");
    }

    public void process() {
        try {
            Long startTime = System.currentTimeMillis();
            System.out.print("Current Time in milliseconds = ");
            System.out.println(startTime);
            try {
                CsvHandler schemaReader = new CsvHandler();

                schemaReader.setSourceFile(new File(readPath + "schemas.csv"));

                ArrayList<Attribute> attributeList = schemaReader.readData();
                System.out.println("Original Schema Size:" + attributeList.size());

                System.out.print("Current Time in milliseconds = ");
                System.out.println(System.currentTimeMillis());
                
                
                //equality check
                leftAttributeLoop:
                for (int leftAttributeNameIterator = 0; leftAttributeNameIterator < attributeList.size(); leftAttributeNameIterator++) {
                    //System.out.println("UHP_MAP size at Start:" + UHP_MAP.size());
                    Attribute attrLeft = attributeList.get(leftAttributeNameIterator);
                    //System.out.println("Processing Attribute:" + attrLeft);
                    boolean checkFurther = true;
                    if (attrLeft.getWords().size() < 1) {
                        attrLeft.setWords(attrLeft.getFullString().split(UhpMapUtils.REGEX_WITH_CASE));
                    }
                    //System.out.println("Just Split:"+attrLeft.getWords().size());
                    rightAttributeLoop:
                    for (int rightAttributeNameIterator = 0; rightAttributeNameIterator < attributeList.size(); rightAttributeNameIterator++) {
                        Attribute attrRight = attributeList.get(rightAttributeNameIterator);

                        //Check 1 - Case Insensitive Search
                        checkFurther = compareCaseInsensitive(attrLeft, attrRight);
                        if (!checkFurther) {
                            continue;
                        }
                        //System.out.println("UHP_MAP size after equality check:" + UHP_MAP.size());

                        // Check 2 - Suffix Tree based longest sequence match
                        compareLongestCommonSubsequenceInFullAttribute(attrLeft, attrRight);

                        //Generate the word lists
                        if (attrRight.getWords().size() < 1) {
                            attrRight.setWords(attrRight.getFullString().split(UhpMapUtils.REGEX_WITH_CASE));
                        }
                        //Replace concept from SNOMED CT
                        if (_isSnomedEnabled) {
                            updateConceptsFromSnomedCt(attrLeft);
                            updateConceptsFromSnomedCt(attrRight);
                        } //Replace concept from UMLS
                        else if (_isUmlsEnabled) {
                            updateConceptsFromUmls(attrLeft);
                            updateConceptsFromUmls(attrRight);
                        }
                        //System.out.println("Update from UMLS:" + attrRight.getWords().size());
                        // Check 3 - Compare UMLS CUI
                        if (_isUmlsEnabled) {
                            compareUmlsCui(attrLeft, attrRight);
                        }
                        //System.out.println("[UMLS CUI comparison]" + attrRight.getWords().size());

                        // Check 4 - Suffix Tree based longest sequence match between
                        // one full attribute and the other's words
                        comparePartialLongestCommonSubsequence(attrLeft, attrRight);
                        //System.out.println("Partial Longest Common Subsequence completed");
                        //System.out.println(attrRight.getWords().size());
                        //Check 5 - ConceptNET comparison
                        if (_isConceptNetEnabled) {
                            //System.out.println("------------------ Starting Concpet net mapping ------------------");
                            ConceptNetThreadedComparator ctc = new ConceptNetThreadedComparator(attrLeft, attrRight);
                            AttributeMap map = ctc.process();
                            //System.out.println("------------------ Concpet net mapping done ------------------");
                            if (map != null) {
                                //System.out.println("Adding a new element after ConceptNet check:" + map);
                                this.UHP_MAP.add(map);
                            }
                            //compareRelatednessFromConceptNet(attrLeft, attrRight);
                        }

                    }//Iterating Schema List for Right hand terms//Iterating Schema List for Right hand terms

                }//Iterating Schema List for Left hand terms//Iterating Schema List for Left hand terms
                System.out.println("Finished Map generation process");
                Iterator hashSetIterator = UHP_MAP.iterator();
                while (hashSetIterator.hasNext()) {
                    System.out.println(hashSetIterator.next());
                }
                System.out.println("UHP_MAP Size:" + UHP_MAP.size());
                //System.out.println(UHP_MAP);
            } catch (IOException ex) {
                Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            Long processCompleteTime = System.currentTimeMillis();
            System.out.print("Current Time in milliseconds = ");
            System.out.println(processCompleteTime);
            File file = new File(writePath);
            file.mkdirs();
            String realpath = writePath + "\\" + "schema_processed" + System.currentTimeMillis() + ".json";
            ObjectMapper UhpMapJson = new ObjectMapper();
            ObjectWriter UhpMapWriter = UhpMapJson.writer(new DefaultPrettyPrinter());
            UhpMapWriter.writeValue(new File(realpath), UHP_MAP);
            Long endTime = System.currentTimeMillis();
            
            System.out.println("Execute Time:");
            System.out.print("Processing Time:");
            System.out.println(processCompleteTime - startTime);
            System.out.print("Total Time:");
            System.out.println(endTime - startTime);
            
        } catch (IOException ex) {
            Logger.getLogger(Matcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //Remove special characters from the string
    private String cleanWord(String word) {
        return word.replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
    }

    //Remove special characters from the attribute
    private String cleanAttribute(Attribute attr) {
        return cleanWord(attr.getFullString());
    }

    //compare schema names
    public boolean isSameSchema(Attribute attrLeft, Attribute attrRight) {
        String schemaLeftName = attrLeft.getTableName().substring(0, attrLeft.getTableName().indexOf("_"));
        String schemaRightName = attrRight.getTableName().substring(0, attrRight.getTableName().indexOf("_"));
        return schemaLeftName.equalsIgnoreCase(schemaRightName);
    }

    //Get concepts from SNOMED-CT
    private void updateConceptsFromSnomedCt(Attribute attr) {
        if (attr.getWords().size() < 1) {
            System.out.println("No words in the attribute");
            return;
        } else {
            for (int wordIte = 0; wordIte < attr.getWords().size(); wordIte++) {
                try {
                    String wordTitle = cleanWord(attr.getWords().get(wordIte).getTitle());
                    if (!snomedCtMap.containsKey(wordTitle)
                            && !snomedCtBlackList.contains(wordTitle)) {
                        String conceptId = snomedHandler.query(wordTitle);
                        if ((conceptId == null || "".equals(conceptId))) {
                            // Unable to get this word skip it next time
                            System.out.println("Error in Item length for :" + wordTitle);
                            snomedCtBlackList.add(wordTitle);
                        } else {
                            //System.out.println("Found in SNOMED CT:" + wordTitle);
                            snomedCtMap.put(wordTitle, conceptId);
                            attr.getWords().get(wordIte).setSnomedCtString(conceptId);
                            //System.out.println("Adding concept id:" + attr.getWords().get(wordIte));
                        }
                    } else {
                        attr.getWords().get(wordIte).setSnomedCtString(snomedCtMap.get(wordTitle));
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    snomedCtBlackList.add(attr.getWords().get(wordIte).getTitle());
                }
            }
        }
    }

    //Get concepts from Umls
    private void updateConceptsFromUmls(Attribute attr) {
        if (attr.getWords().size() < 1) {
            System.out.println("No words in the attribute");
        } else {
            for (int wordIte = 0; wordIte < attr.getWords().size(); wordIte++) {
                try {
                    String wordTitle = cleanWord(attr.getWords().get(wordIte).getTitle());
                    if (!umlsMap.containsKey(wordTitle)
                            && !umlsBlackList.contains(wordTitle)) {
                        List<Concept> concept = umlsHandler.query(wordTitle);
                        if ((concept == null || concept.size() < 1)) {
                            // Unable to get this word skip it next time
                            System.out.println("Error in Item length for:" + wordTitle);
                            umlsBlackList.add(wordTitle);
                        } else {
                            //System.out.println("Found in UMLS:" + wordTitle);
                            umlsMap.put(wordTitle, concept);
                            attr.getWords().get(wordIte).setUmlsConcept(concept);
                            //System.out.println("Adding CUI:" + attr.getWords().get(wordIte));
                        }
                    } else {
                        attr.getWords().get(wordIte).setUmlsConcept(umlsMap.get(wordTitle));
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    umlsBlackList.add(attr.getWords().get(wordIte).getTitle());
                }
            }
        }
    }

    //CHECK 1: Ignore case while comparing
    private boolean compareCaseInsensitive(Attribute attrLeft, Attribute attrRight) {
        //Ignore attributes in the same table
        if (isSameSchema(attrLeft, attrRight)) {
            return true;
        }

        String cleanAttrLeftString = cleanAttribute(attrLeft);
        String cleanAttrRightString = cleanAttribute(attrRight);

        if (cleanAttrLeftString.equalsIgnoreCase(cleanAttrRightString)) {
            //System.out.println(attrLeft + " == " + attrRight);
            UHP_MAP.add(new AttributeMap(attrLeft, attrRight, SchemaRelation.EQUAL, "equalsIgnoreCase", 1.0));
            return false;
        }
        return true;
    }

    //Case 2 - Longest Common Subsequence In Full Attribute Name
    private void compareLongestCommonSubsequenceInFullAttribute(Attribute attrLeft, Attribute attrRight) {
        if (attrLeft == attrRight) {
            return;
        }
        //Ignore attributes in the same table
        if (isSameSchema(attrLeft, attrRight)) {
            return;
        }

        String cleanAttrLeftString = cleanAttribute(attrLeft);
        String cleanAttrRightString = cleanAttribute(attrRight);

        LCSubstringSolver solver = new LCSubstringSolver(new DefaultCharSequenceNodeFactory());
        solver.add(cleanAttrLeftString);
        solver.add(cleanAttrRightString);
        String longestCommonSubstring = CharSequences.toString(solver.getLongestCommonSubstring());

        // Some similarity in the character subset was found, so lets check the threshold and add as a map
        if (longestCommonSubstring.length() > 1) {
            double threshold1 = (cleanAttrLeftString.length() / 2) * 3;
            //double threshold2 = (cleanAttrRightString.length() / 2) * 3;
            if (longestCommonSubstring.length() > threshold1) {
                double confidence = 1 - ((cleanAttrLeftString.length() - longestCommonSubstring.length()) / cleanAttrLeftString.length());
                AttributeMap sc = new AttributeMap(attrLeft, attrRight,
                        SchemaRelation.SUBSUMPTION, "SuffixTreeFullAttribute",
                        confidence);
                sc.setComments(longestCommonSubstring);
                UHP_MAP.add(sc);
            }
//            if (longestCommonSubstring.length() > threshold2) {
//                double confidence = 1 - ((cleanAttrRightString.length() - longestCommonSubstring.length()) / cleanAttrRightString.length());
//                AttributeMap sc = new AttributeMap(attrLeft, attrRight,
//                        SchemaRelation.SUBSUMPTION, "SuffixTreeFullAttribute",
//                        confidence);
//                sc.setComments(longestCommonSubstring);
//                UHP_MAP.add(sc);
//
//            }
        }
    }

    //Case 3 - Partial Longest Common Subsequence using words in Right attribute and full Left attribute
    private void comparePartialLongestCommonSubsequence(Attribute attrLeft, Attribute attrRight) {
        if (attrLeft == attrRight) {
            return;
        }
        //Ignore attributes in the same table
        if (isSameSchema(attrLeft, attrRight)) {
            return;
        }

        String cleanAttrLeftString = cleanAttribute(attrLeft).toLowerCase();

        List<Word> words = attrRight.getWords();
        boolean[] matchStatus = new boolean[words.size()];
        String[] matchString = new String[words.size()];
        for (int k = 0; k < words.size(); k++) {
            LCSubstringSolver solver = new LCSubstringSolver(new DefaultCharSequenceNodeFactory());
            solver.add(cleanAttrLeftString);
            solver.add(words.get(k).getTitle().toLowerCase());
            String longestCommonSubstring = CharSequences.toString(solver.getLongestCommonSubstring());
            if (longestCommonSubstring.length() > 1) {
                double threshold = (words.get(k).getTitle().length() / 3) * 2;
                matchStatus[k] = longestCommonSubstring.length() > threshold;
                matchString[k] = longestCommonSubstring;
            }
        }

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
            AttributeMap sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.SUBSUMPTION, "SuffixTreeParts", 0.6);
            sb.setComments(Arrays.toString(matchString));

            //System.out.println("Adding a new element after partial word check");
            UHP_MAP.add(sb);
        }
    }

//    private void compareRelatednessFromConceptNet(Attribute attrLeft, Attribute attrRight) {
//        if (attrLeft == attrRight) {
//            return;
//        }
//        //Ignore attributes in the same table
//        if (isSameSchema(attrLeft, attrRight)) {
//            return;
//        }
//
//        List<Word> wordsLeft = attrLeft.getWords();
//        List<Word> wordsRight = attrRight.getWords();
//
//        // Build the ConceptNetMap
//        //Store status and string of each word in the left string with all corresponding words of the right string
//        ArrayList<WordMap> wordsMap = new ArrayList();
//        boolean finalMatchTest = true;
//        double totalWeight = 0;
//        int countRelation = 0;
//
//        //ArrayList<Edge> relatedness;
//        for (Word wordObjLeft : wordsLeft) {
//            String wordStrLeft = wordObjLeft.getTitle().replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
//            if (wordStrLeft.length() < 2) {
//                continue;
//            }
////            if (conceptNetBlackList.contains(wordStrLeft)) {
////                continue;
////            }
//            for (Word wordObjRight : wordsRight) {
//                String wordStrRight = wordObjRight.getTitle().replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
//                if (wordStrRight.length() < 2) {
//                    continue;
//                }
////                if (conceptNetBlackList.contains(wordStrRight)) {
////                    continue;
////                }
//
//                RelatednessNode relatedness = conceptNetHandler.queryRelatedness(wordStrLeft, wordStrRight);
//                if (relatedness != null && relatedness.getWeight() > 0) {
//                    WordMap newWordMap;
//                    if (relatedness.getWeight() > 0.9) {
//                        newWordMap = new WordMap(wordObjLeft, wordObjRight, SchemaRelation.EQUAL, "ConceptNet", relatedness.getWeight());
//                    } else {
//                        newWordMap = new WordMap(wordObjLeft, wordObjRight, SchemaRelation.AMBIGIOUS, "ConceptNet", relatedness.getWeight());
//                    }
//                    newWordMap.setComments(relatedness.toString());
//                    wordsMap.add(newWordMap);
//                    finalMatchTest = finalMatchTest & true;
//                    totalWeight += relatedness.getWeight();
//                    countRelation++;
//                } else {
//                    finalMatchTest = finalMatchTest & false;
//                }
//            }
//        }
//        if (countRelation == 0) {
//            return;
//        }
//
//        double averageWeight = totalWeight / countRelation;
//
//        AttributeMap sb;
//        if (averageWeight > 0.9) {
//            sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.EQUAL, "ConceptNet", averageWeight);
//        } else {
//            sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.AMBIGIOUS, "ConceptNet", averageWeight);
//        }
//        sb.setComments(wordsMap.toString());
//        //System.out.println("Adding a new element after ConceptNet check");
//        UHP_MAP.add(sb);
//
//    }

    private void compareUmlsCui(Attribute attrLeft, Attribute attrRight) {
        if (attrLeft == attrRight) {
            return;
        }
        //Ignore attributes in the same table
        if (isSameSchema(attrLeft, attrRight)) {
            return;
        }

        List<Word> wordsLeft = attrLeft.getWords();
        List<Word> wordsRight = attrRight.getWords();
        List<WordMap> wordsMap = new ArrayList();
        double totalWeight = 0;
        int countRelation = 0;

        for (int wordIte = 0; wordIte < wordsLeft.size(); wordIte++) {
            Word wordObjLeft = wordsLeft.get(wordIte);
            String wordStrLeft = wordObjLeft.getTitle().replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
            if (wordStrLeft.length() < 2) {
                continue;
            }

            Collection leftConcepts = wordObjLeft.getUmlsConcept();
            if (leftConcepts == null || leftConcepts.size() < 1) {
                continue;
            }
            for (Word wordObjRight : wordsRight) {
                String wordStrRight = wordObjRight.getTitle().replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
                if (wordStrRight.length() < 2) {
                    continue;
                }
                
                if(wordStrRight.equalsIgnoreCase(wordStrLeft)){
                    WordMap m = new WordMap(wordObjRight, wordObjLeft, SchemaRelation.EQUAL, "CASE_Insensitive_Compare", 1.0);
                    m.setComments("Found during UMLS CUI match");
                    wordsMap.add(m);
                    continue;
                }
                
                Collection rightConcepts = wordObjLeft.getUmlsConcept();
                if (rightConcepts == null || rightConcepts.size() < 1) {
                    continue;
                }
                //retain only similar elements
//                System.out.print("LeftConcepts 1"+wordObjLeft);
//                System.out.println(leftConcepts);
//                System.out.print("RightConcepts 1"+wordObjRight);
//                System.out.println(rightConcepts);
                
                Collection commonConcepts = (Collection) rightConcepts.stream().filter(leftConcepts::contains).collect(Collectors.toList());
                
                //rightConcepts.retainAll(leftConcepts);
//                System.out.print("Common Concepts 2");
//                System.out.println(commonConcepts);
//                
                if (commonConcepts!=null && commonConcepts.size() > 0) {
                    WordMap m = new WordMap(wordObjRight, wordObjLeft, SchemaRelation.SUBSUMPTION, "UMLS_CUI_Compare", 1.0);
                    m.setComments(commonConcepts.toString());
                    wordsMap.add(m);
                    totalWeight++;
                    countRelation++;
                }

            }

        }
        //Compare the CUI of the two words
        if (totalWeight > 0) {
            //Should be always 1.0
            double averageWeight = totalWeight / countRelation;

            AttributeMap sb;
            //if (averageWeight > 0.9) {
            sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.EQUAL, "UMLS_CUI", averageWeight);
            //} else {
            //    sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.AMBIGIOUS, "ConceptNet", averageWeight);
            //}
            try {
//                if(wordsMap.size()<100){
//                    sb.setComments(wordsMap.toString());
//                }else{
                StringBuilder b = new StringBuilder();
                wordsMap.forEach(b::append);
                sb.setComments(b.toString());
//                }
            } catch (OutOfMemoryError er) {
                System.out.println("[UMLS_Compare]Out of memory error********************");
                System.out.println("[UMLS_Compare] input 1:" + attrLeft);
                System.out.println("[UMLS_Compare] words 1:" + wordsLeft.size());
                System.out.println("[UMLS_Compare] input 2:" + attrRight);
                System.out.println("[UMLS_Compare] words 2:" + wordsRight.size());
                System.out.println("[UMLS_Compare] Relations:" + countRelation);
                System.out.println("[UMLS_Compare] Out of memory error for size:" + wordsMap.size());

            }
            //System.out.println("Adding a new element after UMLS CUI check:" + sb);
            UHP_MAP.add(sb);
        }

    }

}
