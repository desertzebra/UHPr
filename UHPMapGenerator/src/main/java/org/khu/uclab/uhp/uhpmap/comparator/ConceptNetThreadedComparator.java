/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.comparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.khu.uclab.uhp.uhpmap.conceptnet.ConceptNetWrapper;
import org.khu.uclab.uhp.uhpmap.conceptnet.RelatednessNode;
import org.khu.uclab.uhp.uhpmap.model.Attribute;
import org.khu.uclab.uhp.uhpmap.model.AttributeMap;
import org.khu.uclab.uhp.uhpmap.model.SchemaRelation;
import org.khu.uclab.uhp.uhpmap.model.Word;
import org.khu.uclab.uhp.uhpmap.model.WordMap;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class ConceptNetThreadedComparator {

    private Attribute attrLeft;
    private Attribute attrRight;
    ExecutorService conceptNetQueryExecutor = Executors.newFixedThreadPool(2);  
    ConceptNetWrapper conceptNetHandler = new ConceptNetWrapper();

    public ConceptNetThreadedComparator(Attribute attrLeft, Attribute attrRight) {
        this.attrLeft = attrLeft;
        this.attrRight = attrRight;
    }

    public Attribute getAttrRight() {
        return attrRight;
    }

    public void setAttrRight(Attribute attrRight) {
        this.attrRight = attrRight;
    }

    public Attribute getAttrLeft() {
        return attrLeft;
    }

    public void setAttrLeft(Attribute attrLeft) {
        this.attrLeft = attrLeft;
    }

    public AttributeMap process() {
        if (attrLeft == attrRight) {
            return null;
        }
        //Ignore attributes in the same table
        if (UhpMapUtils.isSameSchema(attrLeft, attrRight)) {
            return null;
        }

        //System.out.println("Starting the word processing");
        List<Word> wordsLeft = attrLeft.getWords();
        List<Word> wordsRight = attrRight.getWords();
//        System.out.println("left attr:"+ attrLeft);
//        System.out.println("right attr:"+ attrRight);
//        System.out.println("left words:"+ wordsLeft);
//        System.out.println("right words:"+ wordsRight);
        // Build the ConceptNetMap
        //Store status and string of each word in the left string with all corresponding words of the right string
        ArrayList<WordMap> wordsMap = new ArrayList();
        boolean finalMatchTest = true;
        double totalWeight = 0;
        int countRelation = 0;
        List<Future<WordMap>> mappingTaskList = new ArrayList<>();
        //ArrayList<Edge> relatedness;
        for (Word wordObjLeft : wordsLeft) {
            String wordStrLeft = wordObjLeft.getTitle().replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
            if (wordStrLeft.length() < 2) {
                continue;
            }
//                if (conceptNetBlackList.contains(wordStrLeft)) {
//                    continue;
//                }
            mappingTaskList.clear();
            //System.out.println("Selected Left word:"+wordStrLeft);
            //ArrayList<WordMap> temp_wordsMap = new ArrayList();
            for (Word wordObjRight : wordsRight) {
                String wordStrRight = wordObjRight.getTitle().replaceAll(UhpMapUtils.REGEX_REPLACE_SPECIAL, "");
                if (wordStrRight.length() < 2) {
                    continue;
                }
                if(wordStrLeft.equalsIgnoreCase(wordStrRight)){
                    WordMap m = new WordMap(wordObjLeft, wordObjRight, SchemaRelation.EQUAL, "CASE_Insensitive_Compare", 1.0);
                    m.setComments("Found during ConceptNet match");
                    wordsMap.add(m);
                    continue;
                }
                //System.out.println("Selected Right word:"+wordStrRight);
                Callable<WordMap> processAttr = () -> {
                    RelatednessNode relatedness = conceptNetHandler.queryRelatedness(wordStrLeft, wordStrRight);
                    if (relatedness != null && relatedness.getWeight() > 0) {
                        WordMap newWordMap;
                        if (relatedness.getWeight() > 0.9) {
                            newWordMap = new WordMap(wordObjLeft, wordObjRight, SchemaRelation.EQUAL, "ConceptNet", relatedness.getWeight());
                        } else {
                            newWordMap = new WordMap(wordObjLeft, wordObjRight, SchemaRelation.AMBIGIOUS, "ConceptNet", relatedness.getWeight());
                        }
                        newWordMap.setComments(relatedness.toString());
                        return newWordMap;
                    } else {
                        return null;
                        //finalMatchTest = finalMatchTest & false;
                    }
                };
                mappingTaskList.add(this.conceptNetQueryExecutor.submit(processAttr));
                
            }

            try {
                //System.out.println("Waiting for results from ConceptNet");
                for (Future<WordMap> task : mappingTaskList) {
                    WordMap map = task.get();
                    if (map != null) {
                        totalWeight += map.getConfidence();
                        countRelation++;
                        wordsMap.add(map);
                    }
                }
                //System.out.println("All result collected");
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ConceptNetThreadedComparator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (countRelation == 0) {
            return null;
        }

        double averageWeight = totalWeight / countRelation;

        AttributeMap sb;
        if (averageWeight > 0.9) {
            sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.EQUAL, "ConceptNet", averageWeight);
        } else {
            sb = new AttributeMap(attrLeft, attrRight, SchemaRelation.AMBIGIOUS, "ConceptNet", averageWeight);
        }
        try{
//                if(wordsMap.size()<100){
//                    sb.setComments(wordsMap.toString());
//                }else{
                    StringBuilder b = new StringBuilder();
                    wordsMap.forEach(b::append);
                    sb.setComments(b.toString());
//                }
            }catch(OutOfMemoryError er){
                System.out.println("*******************[ConceptNET] Out of memory error********************");
                System.out.println("[ConceptNET] input 1:"+attrLeft);
                System.out.println("[ConceptNET] words 1:"+wordsLeft.size());
                System.out.println("[ConceptNET] input 2:"+attrRight);
                System.out.println("[ConceptNET] words 2:"+wordsRight.size());
                System.out.println("[ConceptNET] Relations:"+countRelation);
                System.out.println("[ConceptNET] Out of memory error for size:"+wordsMap.size());
                
            }
        //sb.setComments(wordsMap.toString());
        //System.out.println("Adding a new element after ConceptNet check:" + sb);
        //UHP_MAP.add(sb);
        return sb;

    }
}
