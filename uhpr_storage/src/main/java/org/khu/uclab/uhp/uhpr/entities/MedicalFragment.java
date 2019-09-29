package org.khu.uclab.uhp.uhpr.entities;

import java.util.HashMap;
import org.khu.uclab.uhp.uhpr.seeder.util.*;

public abstract class MedicalFragment {
	private String fragmentId;	// fragId should be different between all fragments
	private String fragmentType;	// the type of fragment
	private String data;
	private String version;

	RandomGenerator rand = new RandomGenerator();
	NameGenerator nameGen = new NameGenerator();
	DateGenerator dateGen = new DateGenerator();
	GenderGenerator genderGen = new GenderGenerator();
	StringGenerator stringGen = new StringGenerator();
	RegionGenerator regionGen = new RegionGenerator();
	
	public MedicalFragment(String fragmentId, String fragmentType, String version) {
		this.fragmentId = fragmentId;
		this.fragmentType = fragmentType;
		this.version = version;
	}
	
	public abstract void setValues(HashMap<String,Object> values);
	
	public abstract void setPrivate(String firstName, String lastName, String dob, int gender);
	
    public abstract void setData();
	
	public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    public String getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(String fragmentType) {
        this.fragmentType = fragmentType;
    }

    public String getData() {
        if(data!=null && !data.isEmpty()){
        }else{
            data = this.toString();
        }
        return data;
    }
    
    public void setData(String data) {
    	this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public abstract String toString();
	
}
