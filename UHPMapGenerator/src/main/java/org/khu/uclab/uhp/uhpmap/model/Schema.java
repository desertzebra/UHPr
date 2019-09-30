/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.model;

/**
 *
 * @author uclab351
 */
public class Schema {
    private String TableName;
    private String AttributeName;

    @Override
    public String toString() {
        return "Schema{" + "TableName=" + TableName + ", AttributeName=" + AttributeName + '}';
    }


    public String getTableName() {
        return TableName;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setAttributeName(String AttributeName) {
        this.AttributeName = AttributeName;
    }
    
}
