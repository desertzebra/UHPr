package org.khu.uclab.uhp.uhpr_model.entities.medicalfragmentschema;

import org.khu.uclab.uhp.uhpr_model.entities.MedicalFragment;

public class Openemr_Demographics extends MedicalFragment {
    // Who
    private String Name;
    private String ExternalID;
    private String DOB;
    private String Sex;
    private String SS;
    private String License;
    private String MaritalStatus;
    private String UserDefined;
    private String BillingNote;
    // Address
    private String Address;
    private String City;
    private String State;
    private String PostalCode;
    private String Country;
    private String MotherName;
    private String EmergencyContact;
    private String EmergencyPhone;
    private String HomePhone;
    private String WorkPhone;
    private String MobilePhone;
    private String ContactEmail;
    private String TrustedEmail;
    // Choices
    private String Provider;
    private String Referring_Provider;
    private String Pharmacy;
    private String HIPPANoticeReceived;
    private String AllowVoiceMessage;
    private String LeaveMessageWith;
    private String AllowMailMessage;
    private String AllowSMS;
    private String AllowEmail;
    private String AllowImmunizationRegistryUse;
    private String AllowImmunizationInfoSharing;
    private String AllowHeartInformationExchange;
    private String AllowPatientPortal;
    private String CareTeam;
    private String CMSPortalLogin;
    private String ImmunizationRegistryStatus;
    private String ImmunizationRegistryStatusEffectiveDate;
    private String PublicityCode;
    private String PublicityCodeEffectiveDate;
    private String ProtectionIndicator;
    private String ProtectionIndicatorEffectiveDate;
    // Stats
    private String Language;
    private String Race;
    private String Ethnicity;
    private String FamilySize;
    private String FinancialReviewDate;
    private String Homeless;
    private String MonthlyIncome;
    private String Interpreter;
    private String MigrantSeasonal;
    private String VFC;
    private String Religion;
    // Misc
    private String DateDecreased;
    private String ReasonDecreased;
    
    public Openemr_Demographics(String fragmentId, String fragmentType, String version) {
    	super(fragmentId, fragmentType, version);
    }
    
    public void setData() {
    	this.setData(this.toString());
    }
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getExternalID() {
        return ExternalID;
    }

    public void setExternalID(String ExternalID) {
        this.ExternalID = ExternalID;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getSS() {
        return SS;
    }

    public void setSS(String SS) {
        this.SS = SS;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String License) {
        this.License = License;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String MaritalStatus) {
        this.MaritalStatus = MaritalStatus;
    }

    public String getUserDefined() {
        return UserDefined;
    }

    public void setUserDefined(String UserDefined) {
        this.UserDefined = UserDefined;
    }

    public String getBillingNote() {
        return BillingNote;
    }

    public void setBillingNote(String BillingNote) {
        this.BillingNote = BillingNote;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String MotherName) {
        this.MotherName = MotherName;
    }

    public String getEmergencyContact() {
        return EmergencyContact;
    }

    public void setEmergencyContact(String EmergencyContact) {
        this.EmergencyContact = EmergencyContact;
    }

    public String getEmergencyPhone() {
        return EmergencyPhone;
    }

    public void setEmergencyPhone(String EmergencyPhone) {
        this.EmergencyPhone = EmergencyPhone;
    }

    public String getHomePhone() {
        return HomePhone;
    }

    public void setHomePhone(String HomePhone) {
        this.HomePhone = HomePhone;
    }

    public String getWorkPhone() {
        return WorkPhone;
    }

    public void setWorkPhone(String WorkPhone) {
        this.WorkPhone = WorkPhone;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String MobilePhone) {
        this.MobilePhone = MobilePhone;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String ContactEmail) {
        this.ContactEmail = ContactEmail;
    }

    public String getTrustedEmail() {
        return TrustedEmail;
    }

    public void setTrustedEmail(String TrustedEmail) {
        this.TrustedEmail = TrustedEmail;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String Provider) {
        this.Provider = Provider;
    }

    public String getReferring_Provider() {
        return Referring_Provider;
    }

    public void setReferring_Provider(String Referring_Provider) {
        this.Referring_Provider = Referring_Provider;
    }

    public String getPharmacy() {
        return Pharmacy;
    }

    public void setPharmacy(String Pharmacy) {
        this.Pharmacy = Pharmacy;
    }

    public String getHIPPANoticeReceived() {
        return HIPPANoticeReceived;
    }

    public void setHIPPANoticeReceived(String HIPPANoticeReceived) {
        this.HIPPANoticeReceived = HIPPANoticeReceived;
    }

    public String getAllowVoiceMessage() {
        return AllowVoiceMessage;
    }

    public void setAllowVoiceMessage(String AllowVoiceMessage) {
        this.AllowVoiceMessage = AllowVoiceMessage;
    }

    public String getLeaveMessageWith() {
        return LeaveMessageWith;
    }

    public void setLeaveMessageWith(String LeaveMessageWith) {
        this.LeaveMessageWith = LeaveMessageWith;
    }

    public String getAllowMailMessage() {
        return AllowMailMessage;
    }

    public void setAllowMailMessage(String AllowMailMessage) {
        this.AllowMailMessage = AllowMailMessage;
    }

    public String getAllowSMS() {
        return AllowSMS;
    }

    public void setAllowSMS(String AllowSMS) {
        this.AllowSMS = AllowSMS;
    }

    public String getAllowEmail() {
        return AllowEmail;
    }

    public void setAllowEmail(String AllowEmail) {
        this.AllowEmail = AllowEmail;
    }

    public String getAllowImmunizationRegistryUse() {
        return AllowImmunizationRegistryUse;
    }

    public void setAllowImmunizationRegistryUse(String AllowImmunizationRegistryUse) {
        this.AllowImmunizationRegistryUse = AllowImmunizationRegistryUse;
    }

    public String getAllowImmunizationInfoSharing() {
        return AllowImmunizationInfoSharing;
    }

    public void setAllowImmunizationInfoSharing(String AllowImmunizationInfoSharing) {
        this.AllowImmunizationInfoSharing = AllowImmunizationInfoSharing;
    }

    public String getAllowHeartInformationExchange() {
        return AllowHeartInformationExchange;
    }

    public void setAllowHeartInformationExchange(String AllowHeartInformationExchange) {
        this.AllowHeartInformationExchange = AllowHeartInformationExchange;
    }

    public String getAllowPatientPortal() {
        return AllowPatientPortal;
    }

    public void setAllowPatientPortal(String AllowPatientPortal) {
        this.AllowPatientPortal = AllowPatientPortal;
    }

    public String getCareTeam() {
        return CareTeam;
    }

    public void setCareTeam(String CareTeam) {
        this.CareTeam = CareTeam;
    }

    public String getCMSPortalLogin() {
        return CMSPortalLogin;
    }

    public void setCMSPortalLogin(String CMSPortalLogin) {
        this.CMSPortalLogin = CMSPortalLogin;
    }

    public String getImmunizationRegistryStatus() {
        return ImmunizationRegistryStatus;
    }

    public void setImmunizationRegistryStatus(String ImmunizationRegistryStatus) {
        this.ImmunizationRegistryStatus = ImmunizationRegistryStatus;
    }

    public String getImmunizationRegistryStatusEffectiveDate() {
        return ImmunizationRegistryStatusEffectiveDate;
    }

    public void setImmunizationRegistryStatusEffectiveDate(String ImmunizationRegistryStatusEffectiveDate) {
        this.ImmunizationRegistryStatusEffectiveDate = ImmunizationRegistryStatusEffectiveDate;
    }

    public String getPublicityCode() {
        return PublicityCode;
    }

    public void setPublicityCode(String PublicityCode) {
        this.PublicityCode = PublicityCode;
    }

    public String getPublicityCodeEffectiveDate() {
        return PublicityCodeEffectiveDate;
    }

    public void setPublicityCodeEffectiveDate(String PublicityCodeEffectiveDate) {
        this.PublicityCodeEffectiveDate = PublicityCodeEffectiveDate;
    }

    public String getProtectionIndicator() {
        return ProtectionIndicator;
    }

    public void setProtectionIndicator(String ProtectionIndicator) {
        this.ProtectionIndicator = ProtectionIndicator;
    }

    public String getProtectionIndicatorEffectiveDate() {
        return ProtectionIndicatorEffectiveDate;
    }

    public void setProtectionIndicatorEffectiveDate(String ProtectionIndicatorEffectiveDate) {
        this.ProtectionIndicatorEffectiveDate = ProtectionIndicatorEffectiveDate;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String Race) {
        this.Race = Race;
    }

    public String getEthnicity() {
        return Ethnicity;
    }

    public void setEthnicity(String Ethnicity) {
        this.Ethnicity = Ethnicity;
    }

    public String getFamilySize() {
        return FamilySize;
    }

    public void setFamilySize(String FamilySize) {
        this.FamilySize = FamilySize;
    }

    public String getFinancialReviewDate() {
        return FinancialReviewDate;
    }

    public void setFinancialReviewDate(String FinancialReviewDate) {
        this.FinancialReviewDate = FinancialReviewDate;
    }

    public String getHomeless() {
        return Homeless;
    }

    public void setHomeless(String Homeless) {
        this.Homeless = Homeless;
    }

    public String getMonthlyIncome() {
        return MonthlyIncome;
    }

    public void setMonthlyIncome(String MonthlyIncome) {
        this.MonthlyIncome = MonthlyIncome;
    }

    public String getInterpreter() {
        return Interpreter;
    }

    public void setInterpreter(String Interpreter) {
        this.Interpreter = Interpreter;
    }

    public String getMigrantSeasonal() {
        return MigrantSeasonal;
    }

    public void setMigrantSeasonal(String MigrantSeasonal) {
        this.MigrantSeasonal = MigrantSeasonal;
    }

    public String getVFC() {
        return VFC;
    }

    public void setVFC(String VFC) {
        this.VFC = VFC;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String Religion) {
        this.Religion = Religion;
    }

    public String getDateDecreased() {
        return DateDecreased;
    }

    public void setDateDecreased(String DateDecreased) {
        this.DateDecreased = DateDecreased;
    }

    public String getReasonDecreased() {
        return ReasonDecreased;
    }

    public void setReasonDecreased(String ReasonDecreased) {
        this.ReasonDecreased = ReasonDecreased;
    }

    @Override
    public String toString() {
        return "Name=" + Name + ", ExternalID=" + ExternalID + ", DOB=" + DOB + ", Sex=" + Sex + ", SS=" + SS + ", License=" + License + ", MaritalStatus=" + MaritalStatus + ", UserDefined=" + UserDefined + ", BillingNote=" + BillingNote + ", Address=" + Address + ", City=" + City + ", State=" + State + ", PostalCode=" + PostalCode + ", Country=" + Country + ", MotherName=" + MotherName + ", EmergencyContact=" + EmergencyContact + ", EmergencyPhone=" + EmergencyPhone + ", HomePhone=" + HomePhone + ", WorkPhone=" + WorkPhone + ", MobilePhone=" + MobilePhone + ", ContactEmail=" + ContactEmail + ", TrustedEmail=" + TrustedEmail + ", Provider=" + Provider + ", Referring_Provider=" + Referring_Provider + ", Pharmacy=" + Pharmacy + ", HIPPANoticeReceived=" + HIPPANoticeReceived + ", AllowVoiceMessage=" + AllowVoiceMessage + ", LeaveMessageWith=" + LeaveMessageWith + ", AllowMailMessage=" + AllowMailMessage + ", AllowSMS=" + AllowSMS + ", AllowEmail=" + AllowEmail + ", AllowImmunizationRegistryUse=" + AllowImmunizationRegistryUse + ", AllowImmunizationInfoSharing=" + AllowImmunizationInfoSharing + ", AllowHeartInformationExchange=" + AllowHeartInformationExchange + ", AllowPatientPortal=" + AllowPatientPortal + ", CareTeam=" + CareTeam + ", CMSPortalLogin=" + CMSPortalLogin + ", ImmunizationRegistryStatus=" + ImmunizationRegistryStatus + ", ImmunizationRegistryStatusEffectiveDate=" + ImmunizationRegistryStatusEffectiveDate + ", PublicityCode=" + PublicityCode + ", PublicityCodeEffectiveDate=" + PublicityCodeEffectiveDate + ", ProtectionIndicator=" + ProtectionIndicator + ", ProtectionIndicatorEffectiveDate=" + ProtectionIndicatorEffectiveDate + ", Language=" + Language + ", Race=" + Race + ", Ethnicity=" + Ethnicity + ", FamilySize=" + FamilySize + ", FinancialReviewDate=" + FinancialReviewDate + ", Homeless=" + Homeless + ", MonthlyIncome=" + MonthlyIncome + ", Interpreter=" + Interpreter + ", MigrantSeasonal=" + MigrantSeasonal + ", VFC=" + VFC + ", Religion=" + Religion + ", DateDecreased=" + DateDecreased + ", ReasonDecreased=" + ReasonDecreased;
    }

}
