package org.khu.uclab.uhp.uhpr.entities;

import java.util.ArrayList;

import org.khu.uclab.uhp.uhpr.seeder.util.MedicalProblemGenerator;

public class Openemr_MedicalProblems extends MedicalFragment {

    private String Title;
    private String Coding;
    private String BeginDate;
    private String EndDate;
    private String Occurrence;
    private String ReferredBy;
    private String Outcome;
    private String Destination;
    
    MedicalProblemGenerator mpg = new MedicalProblemGenerator();

    public Openemr_MedicalProblems(String fragmentId, String fragmentType, String version) {
    	super(fragmentId, fragmentType, version);
    }
    
    public void setPrivate(String firstName, String lastName, String dob, int gender) {}
    
    public void setRandomFields() {
		ArrayList<String> dates = dateGen.getRandomPeriodicDateInYear(2018);
		
		Title = mpg.getRandomProblem();
		Coding = "";
		BeginDate = dates.get(0);
		EndDate = dates.get(1);
		Occurrence = mpg.getRandomOccurrence();
		ReferredBy = "";
		Outcome = mpg.getRandomOutcome();
		Destination = "";
    	
    }
    
    public void setData() {
    	this.setData(this.toString());
    }
    
    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getCoding() {
        return Coding;
    }

    public void setCoding(String Coding) {
        this.Coding = Coding;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String BeginDate) {
        this.BeginDate = BeginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getOccurrence() {
        return Occurrence;
    }

    public void setOccurrence(String Occurrence) {
        this.Occurrence = Occurrence;
    }

    public String getReferredBy() {
        return ReferredBy;
    }

    public void setReferredBy(String ReferredBy) {
        this.ReferredBy = ReferredBy;
    }

    public String getOutcome() {
        return Outcome;
    }

    public void setOutcome(String Outcome) {
        this.Outcome = Outcome;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    @Override
    public String toString() {
        return "Title=" + Title + ", Coding=" + Coding + ", BeginDate=" + BeginDate + ", EndDate=" + EndDate + ", Occurrence=" + Occurrence + ", ReferredBy=" + ReferredBy + ", Outcome=" + Outcome + ", Destination=" + Destination;
    }

}
