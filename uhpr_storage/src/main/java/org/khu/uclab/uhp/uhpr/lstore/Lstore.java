package org.khu.uclab.uhp.uhpr.lstore;

import java.util.ArrayList;

public class Lstore {
    private ArrayList<UhprIndex> UhprList = new ArrayList<UhprIndex>();

    public ArrayList<UhprIndex> getUhprList() {
        return UhprList;
    }

    public void setUhprList(ArrayList<UhprIndex> UhprList) {
        this.UhprList = UhprList;
    }
    
    public void addUhprItem(UhprIndex ui){
        this.UhprList.add(ui);
    }
    
    @Override
    public String toString() {
        return "Lstore{" + "UhprList=" + UhprList + '}';
    }
    
}
