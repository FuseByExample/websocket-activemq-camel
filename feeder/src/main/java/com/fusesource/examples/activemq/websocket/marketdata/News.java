package com.fusesource.examples.activemq.websocket.marketdata;

import java.io.Serializable;

public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String symbol;
    protected String title;
    protected String info;
    protected String picture;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
