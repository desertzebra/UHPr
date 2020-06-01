/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhpr.uhprmap.comparator;

import org.khu.uclab.uhpr.uhprmap.model.Attribute;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class UhprMapUtils {
    //String REGEX_WITH_CASE = "(?<=.)(?=\\p{Lu})|(?<=.)(?=\\d)|(?<=.)(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
    public final static String REGEX_WITH_CASE = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])|(?<=.)(?=\\\\d)|(?<=.)(?=[`~!@#$%^&*()\\\\-_=+\\\\\\\\|\\\\[{\\\\]};:'\\\",<.>/?])";
    public final static String REGEX_REPLACE_SPECIAL = "(?=[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])";
    public final static String REGEX_REPLACE_CONCEPTS = "<<.*>>";
    //String ALLACRONYM_URL = "https://www.allacronyms.com";
    //String SNOMED_CT_URL = "https://browser.ihtsdotools.org/snowstorm/snomed-ct/browser/MAIN/2020-03-09/descriptions?&limit=100&active=true&conceptActive=true&lang=english";
    
    
    
    //compare schema names
    public static boolean isSameSchema(Attribute attrLeft, Attribute attrRight) {
        String schemaLeftName = attrLeft.getTableName().substring(0, attrLeft.getTableName().indexOf("_"));
        String schemaRightName = attrRight.getTableName().substring(0, attrRight.getTableName().indexOf("_"));
        return schemaLeftName.equalsIgnoreCase(schemaRightName);
    }

}
