/*
 * http://www.jkurlandski.com/jkurlandskiWebsite/Index/Professional/Web/conceptNetCode.html
 */
package org.khu.uclab.uhp.uhpmap.conceptnet;

/**
 *
 * @author Fahad Ahmed Satti
 */
public enum Relation {
    Antonym("is the opposite of"), NotAntonym("is not the opposite of"),
    Synonym("is symmetric with"), NotSynonym("is not symmetric with"), 
    AtLocation("is at"), NotAtLocation("is not at"), 
    CapableOf("is capable of"), NotCapableOf("is not capable of"), 
    Causes("causes"), NotCauses("does not cause"), 
    DefinedAs("is defined as"), NotDefinedAs("is not defined as"), 
    DerivedFrom("is derived from"), NotDerivedFrom("is not derived from"),
    HasA("has a"), NotHasA("doesn't have a"), 
    HasContext("occurs in the context of"), NotHasContext("does not occur in the context of"), 
    HasPrerequisite("has a prerequisite of"), NotHasPrerequisite("does not have a prerequisite of"), 
    HasProperty("has the property of"), NotHasProperty("does not have the property of"), 
    HasSubevent("has a subevent of"), NotHasSubevent("does not have a subevent of"), 
    IsA("is a"), NotIsA("is not a"), 
    MemberOf("is a member of"), NotMemberOf("is not a member of"), 
    PartOf("is part of"), NotPartOf("is not part of"), 
    RelatedTo("is related to"), NotRelatedTo("is not related to"), 
    SimilarTo("is similar to"), NotSimilarTo("is not similar to"), 
    TranslationOf("is a translation of"), NotTranslationOf("is not a translation of"), 
    UsedFor("is used for"), NotUsedFor("is not used for"),
    MannerOf("is a specific way to do"), NotMannerOf("is not a specific way to do"),
    FormOf("is an inflected form of"), NotFormOf("is a root word of"),
    Other("undefined");
    
    private String gloss;

    Relation(String str)   {
        gloss = str;
    }
    
    @Override
    public String toString()    {
        return gloss;
    }
}
