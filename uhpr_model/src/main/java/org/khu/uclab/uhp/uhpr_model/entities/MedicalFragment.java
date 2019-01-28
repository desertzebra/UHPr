package org.khu.uclab.uhp.uhpr_model.entities;

public class MedicalFragment {

    private String fragmentId;	// fragId should be different between all fragments
    private String fragmentType;	// the type of fragment
    private String data;
    private String version;

    public MedicalFragment(String fragmentId, String fragmentType, String version) {
        this.fragmentId = fragmentId;
        this.fragmentType = fragmentType;
        this.version = version;
    }

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


}
