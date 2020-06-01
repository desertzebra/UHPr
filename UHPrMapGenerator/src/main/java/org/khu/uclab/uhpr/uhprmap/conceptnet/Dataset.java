/*
 * http://www.jkurlandski.com/jkurlandskiWebsite/Index/Professional/Web/conceptNetCode.html
 */
package org.khu.uclab.uhpr.uhprmap.conceptnet;

/**
 *
 * @author Fahad Ahmed Satti
 */
public enum Dataset {
    ConceptNet, DBPedia, GlobalMind, JmDict, ReVerb, Verbosity, Wiktionary, WordNet, Umbel, OpenCyC, CC_CEDICT, EMOJI;
    
    public static Dataset getDataset(String input)  {
        String tempStr = input.toLowerCase();
        if(tempStr.matches(".*conceptnet.*"))    {
            return Dataset.ConceptNet;
        }
        else if(tempStr.matches(".*dbpedia.*"))    {
            return Dataset.DBPedia;
        }
        else if(tempStr.matches(".*globalmind.*"))    {
            return Dataset.GlobalMind;
        }
        else if(tempStr.matches(".*jmdict.*"))    {
            return Dataset.JmDict;
        }
        else if(tempStr.matches(".*reverb.*"))    {
            return Dataset.ReVerb;
        }
        else if(tempStr.matches(".*verbosity.*"))    {
            return Dataset.Verbosity;
        }
        else if(tempStr.matches(".*wiktionary.*"))    {
            return Dataset.Wiktionary;
        }
        else if(tempStr.matches(".*wordnet.*"))    {
            return Dataset.WordNet;
        }
        else if(tempStr.matches(".*umbel.*"))    {
            return Dataset.Umbel;
        }
        else if (tempStr.matches(".*opencyc.*")) {
            return Dataset.OpenCyC;
        }
        else if (tempStr.matches(".*cc_cedict.*")) {
            return Dataset.CC_CEDICT;
        }
        else if (tempStr.matches(".*emoji.*")) {
            return Dataset.EMOJI;
        }
        
        throw new IllegalArgumentException("Dataset not found for input: " + tempStr);
    }
}
