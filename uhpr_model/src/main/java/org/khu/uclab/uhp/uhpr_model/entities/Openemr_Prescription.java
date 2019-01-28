package org.khu.uclab.uhp.uhpr_model.entities;

public class Openemr_Prescription extends MedicalFragment {

    private String Patient_ID;
    private String Patient_Name;
    private String Currently_Active;
    private String Starting_Date;
    private String Provider;
    private String Drug;
    private String Quantity;
    private String Medicine_Units;
    private String Directions;
    private String Refills;
    private String Notes;

    public Openemr_Prescription(String fragmentId, String fragmentType, String version) {
        super(fragmentId, fragmentType, version);
    }

    @Override
    public String toString() {
        return "Openemr_Prescription{" + "Patient_ID=" + Patient_ID + ", Patient_Name=" + Patient_Name + ", Currently_Active=" + Currently_Active + ", Starting_Date=" + Starting_Date + ", Provider=" + Provider + ", Drug=" + Drug + ", Quantity=" + Quantity + ", Medicine_Units=" + Medicine_Units + ", Directions=" + Directions + ", Refills=" + Refills + ", Notes=" + Notes + '}';
    }

    public String getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(String Patient_ID) {
        this.Patient_ID = Patient_ID;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String Patient_Name) {
        this.Patient_Name = Patient_Name;
    }

    public String getCurrently_Active() {
        return Currently_Active;
    }

    public void setCurrently_Active(String Currently_Active) {
        this.Currently_Active = Currently_Active;
    }

    public String getStarting_Date() {
        return Starting_Date;
    }

    public void setStarting_Date(String Starting_Date) {
        this.Starting_Date = Starting_Date;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String Provider) {
        this.Provider = Provider;
    }

    public String getDrug() {
        return Drug;
    }

    public void setDrug(String Drug) {
        this.Drug = Drug;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getMedicine_Units() {
        return Medicine_Units;
    }

    public void setMedicine_Units(String Medicine_Units) {
        this.Medicine_Units = Medicine_Units;
    }

    public String getDirections() {
        return Directions;
    }

    public void setDirections(String Directions) {
        this.Directions = Directions;
    }

    public String getRefills() {
        return Refills;
    }

    public void setRefills(String Refills) {
        this.Refills = Refills;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

}
