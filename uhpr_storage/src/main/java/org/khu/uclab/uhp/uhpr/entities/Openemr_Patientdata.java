package org.khu.uclab.uhp.uhpr.entities;

public class Openemr_Patientdata extends MedicalFragment {

    private long pid;
    private String title;
    private String language;
    private String financial;
    private String fname;
    private String lname;
    private String mname;
    private String DOB;
    private String street;
    private String postal_code;
    private String city;
    private String state;
    private String country;
    private String country_code;
    private String drivers_license;
    private String ss;
    private String occupation;
    private String phone_home;
    private String phone_cell;
    private String status;	// marriage
    private String date;
    private String sex;
    private String email;
    private String race;
    private long monthly_income;
    private String billing_note;
    private String pubid;

    public Openemr_Patientdata(String fragmentId, String fragmentType, String version) {
        super(fragmentId, fragmentType, version);
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getDrivers_license() {
        return drivers_license;
    }

    public void setDrivers_license(String drivers_license) {
        this.drivers_license = drivers_license;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone_home() {
        return phone_home;
    }

    public void setPhone_home(String phone_home) {
        this.phone_home = phone_home;
    }

    public String getPhone_cell() {
        return phone_cell;
    }

    public void setPhone_cell(String phone_cell) {
        this.phone_cell = phone_cell;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public long getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(long monthly_income) {
        this.monthly_income = monthly_income;
    }

    public String getBilling_note() {
        return billing_note;
    }

    public void setBilling_note(String billing_note) {
        this.billing_note = billing_note;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    @Override
    public String toString() {
        return "Openemr_Patientdata{" + "pid=" + pid + ", title=" + title + ", language=" + language + ", financial=" + financial + ", fname=" + fname + ", lname=" + lname + ", mname=" + mname + ", DOB=" + DOB + ", street=" + street + ", postal_code=" + postal_code + ", city=" + city + ", state=" + state + ", country=" + country + ", country_code=" + country_code + ", drivers_license=" + drivers_license + ", ss=" + ss + ", occupation=" + occupation + ", phone_home=" + phone_home + ", phone_cell=" + phone_cell + ", status=" + status + ", date=" + date + ", sex=" + sex + ", email=" + email + ", race=" + race + ", monthly_income=" + monthly_income + ", billing_note=" + billing_note + ", pubid=" + pubid + '}';
    }

    @Override
    public void setRandomFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPrivate(String firstName, String lastName, String dob, int gender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setData() {
    	this.setData(this.toString());
    }

}
