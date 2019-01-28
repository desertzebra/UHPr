package org.khu.uclab.uhp.uhpr.entities;

import org.khu.uclab.uhp.uhpr.seeder.util.PrescriptionGenerator;

public class Openemr_Prescription extends MedicalFragment {
	
	String Patient_ID;
	String Patient_Name;
	String Currently_Active;
	String Starting_Date;
	String Provider;
	String Drug;
	String Quantity;
	String Medicine_Units;
	String Directions;
	String Refills;
	String Notes;
	
	PrescriptionGenerator pg = new PrescriptionGenerator();
	
	public Openemr_Prescription(String fragmentId, String fragmentType, String version) {
    	super(fragmentId, fragmentType, version);
    }
	
	public void setRandomFields() {
		Patient_ID = "";
		Patient_Name = "";	// will be set by setPrivate();
		Currently_Active = stringGen.getRandomSign();
		Starting_Date = dateGen.getRandomDateInYear(2018);
		Provider = nameGen.getRandomName(genderGen.getRandomGender());
		Drug = pg.getRandomDrug();
		Quantity = String.valueOf(rand.randInt(1, 4));
		Medicine_Units = pg.getRandomUnit();
		Refills = stringGen.getRandomNumber();
		Notes = "";
	}
	
	public void setPrivate(String firstName, String lastName, String dob, int gender) {
		Patient_Name = firstName + " " + lastName;
	}
	
    public void setData() {
    	this.setData(this.toString());
    }
	
	public String getPatient_ID() {
		return Patient_ID;
	}
	public void setPatient_ID(String patient_ID) {
		Patient_ID = patient_ID;
	}
	public String getPatient_Name() {
		return Patient_Name;
	}
	public void setPatient_Name(String patient_Name) {
		Patient_Name = patient_Name;
	}
	public String getCurrently_Active() {
		return Currently_Active;
	}
	public void setCurrently_Active(String currently_Active) {
		Currently_Active = currently_Active;
	}
	public String getStarting_Date() {
		return Starting_Date;
	}
	public void setStarting_Date(String starting_Date) {
		Starting_Date = starting_Date;
	}
	public String getProvider() {
		return Provider;
	}
	public void setProvider(String provider) {
		Provider = provider;
	}
	public String getDrug() {
		return Drug;
	}
	public void setDrug(String drug) {
		Drug = drug;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getMedicine_Units() {
		return Medicine_Units;
	}
	public void setMedicine_Units(String medicine_Units) {
		Medicine_Units = medicine_Units;
	}
	public String getDirections() {
		return Directions;
	}
	public void setDirections(String directions) {
		Directions = directions;
	}
	public String getRefills() {
		return Refills;
	}
	public void setRefills(String refills) {
		Refills = refills;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}

	@Override
	public String toString() {
		return "Patient_ID=" + Patient_ID + ", Patient_Name=" + Patient_Name
				+ ", Currently_Active=" + Currently_Active + ", Starting_Date=" + Starting_Date + ", Provider="
				+ Provider + ", Drug=" + Drug + ", Quantity=" + Quantity + ", Medicine_Units=" + Medicine_Units
				+ ", Directions=" + Directions + ", Refills=" + Refills + ", Notes=" + Notes;
	}
}
