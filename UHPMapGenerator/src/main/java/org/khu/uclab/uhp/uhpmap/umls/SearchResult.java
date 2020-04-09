/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.uhp.uhpmap.umls;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class SearchResult {

    private String ui;
    private String name;
    private String uri;
    private String rootSource;

    //getters
    public String getUi() {

        return this.ui;
    }

    public String getName() {

        return this.name;
    }

    public String getUri() {

        return this.uri;
    }

    public String getRootSource() {

        return this.rootSource;
    }

    //setters
    private void setUi(String ui) {

        this.ui = ui;
    }

    private void setName(String name) {

        this.name = name;
    }

    private void setUri(String uri) {

        this.uri = uri;
    }

    private void setRootSource(String rootSource) {

        this.rootSource = rootSource;
    }
}
