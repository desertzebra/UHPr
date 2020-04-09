package org.khu.uclab.uhp.uhpr_model.entities.medicalfragmentschema;

import org.khu.uclab.uhp.uhpr_model.entities.MedicalFragment;

public class Krsiloemr_tblPatient extends MedicalFragment {

    private String PatientID;
    private int PatientMRNNo;
    private String PatientName;
    private String DateOfBirth;
    private int Age;
    private String Gender;
    private int SymptomsAndSigns;
    private int ClinicalHistory;
    private int PhysicalExam;
    private int ECG;
    private float NTproBNP;
    private float BNP;
    private float LVEF;
    private float LAVI;
    private float LVMI;
    private float Ee;
    private float eSeptal;
    private float LongitudinalStrain;
    private float TRV;
    private String EncounterDate;
    
    public Krsiloemr_tblPatient(String fragmentId, String fragmentType, String version) {
    	super(fragmentId, fragmentType, version);
    }
    
    
    public void setData() {
    	this.setData(this.toString());
    }
    
    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }

    public int getPatientMRNNo() {
        return PatientMRNNo;
    }

    public void setPatientMRNNo(int PatientMRNNo) {
        this.PatientMRNNo = PatientMRNNo;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public int getSymptomsAndSigns() {
        return SymptomsAndSigns;
    }

    public void setSymptomsAndSigns(int SymptomsAndSigns) {
        this.SymptomsAndSigns = SymptomsAndSigns;
    }

    public int getClinicalHistory() {
        return ClinicalHistory;
    }

    public void setClinicalHistory(int ClinicalHistory) {
        this.ClinicalHistory = ClinicalHistory;
    }

    public int getPhysicalExam() {
        return PhysicalExam;
    }

    public void setPhysicalExam(int PhysicalExam) {
        this.PhysicalExam = PhysicalExam;
    }

    public int getECG() {
        return ECG;
    }

    public void setECG(int ECG) {
        this.ECG = ECG;
    }

    public float getNTproBNP() {
        return NTproBNP;
    }

    public void setNTproBNP(float NTproBNP) {
        this.NTproBNP = NTproBNP;
    }

    public float getBNP() {
        return BNP;
    }

    public void setBNP(float BNP) {
        this.BNP = BNP;
    }

    public float getLVEF() {
        return LVEF;
    }

    public void setLVEF(float LVEF) {
        this.LVEF = LVEF;
    }

    public float getLAVI() {
        return LAVI;
    }

    public void setLAVI(float LAVI) {
        this.LAVI = LAVI;
    }

    public float getLVMI() {
        return LVMI;
    }

    public void setLVMI(float LVMI) {
        this.LVMI = LVMI;
    }

    public float getEe() {
        return Ee;
    }

    public void setEe(float Ee) {
        this.Ee = Ee;
    }

    public float geteSeptal() {
        return eSeptal;
    }

    public void seteSeptal(float eSeptal) {
        this.eSeptal = eSeptal;
    }

    public float getLongitudinalStrain() {
        return LongitudinalStrain;
    }

    public void setLongitudinalStrain(float LongitudinalStrain) {
        this.LongitudinalStrain = LongitudinalStrain;
    }

    public float getTRV() {
        return TRV;
    }

    public void setTRV(float TRV) {
        this.TRV = TRV;
    }

    public String getEncounterDate() {
        return EncounterDate;
    }

    public void setEncounterDate(String EncounterDate) {
        this.EncounterDate = EncounterDate;
    }

    @Override
    public String toString() {
        return "PatientID=" + PatientID + ", PatientMRNNo=" + PatientMRNNo + ", PatientName=" + PatientName + ", DateOfBirth=" + DateOfBirth + ", Age=" + Age + ", Gender=" + Gender + ", SymptomsAndSigns=" + SymptomsAndSigns + ", ClinicalHistory=" + ClinicalHistory + ", PhysicalExam=" + PhysicalExam + ", ECG=" + ECG + ", NTproBNP=" + NTproBNP + ", BNP=" + BNP + ", LVEF=" + LVEF + ", LAVI=" + LAVI + ", LVMI=" + LVMI + ", Ee=" + Ee + ", eSeptal=" + eSeptal + ", LongitudinalStrain=" + LongitudinalStrain + ", TRV=" + TRV + ", EncounterDate=" + EncounterDate;
    }

}
