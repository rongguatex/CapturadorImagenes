/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.entidades;

/**
 *
 * @author RGALICIA
 */
public class WebCamInfo {

    private String webCamName;
    private int webCamIndex;

    public String getWebCamName() {
        return webCamName;
    }

    public void setWebCamName(String webCamName) {
        this.webCamName = webCamName;
    }

    public int getWebCamIndex() {
        return webCamIndex;
    }

    public void setWebCamIndex(int webCamIndex) {
        this.webCamIndex = webCamIndex;
    }

    @Override
    public String toString() {
        return "WebCamInfo{" + "webCamName=" + webCamName + ", webCamIndex=" + webCamIndex + '}';
    }
    
}
