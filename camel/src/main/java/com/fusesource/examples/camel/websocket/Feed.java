package com.fusesource.examples.camel.websocket;

import com.sun.syndication.feed.rss.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feed {

    private String title = null;
    private String link = null;
    private String description = null;
    private String webMaster = null;
    private String lastBuildDate = null;
    public List<Item> items = new ArrayList<Item>();
    private List<Map<String, String>> itemsForList = null;

    public Feed(){}

    public Feed(String title, String link, String description, String webMaster, String lastBuildDate){
        this.title = title;
        this.link = link;
        this.description = description;
        this.webMaster = webMaster;
        this.lastBuildDate = lastBuildDate;
    }

    public List getItemsForList(){
        itemsForList = new ArrayList<Map<String, String>>();
        int size = items.size();
        for(int i=0; i<size; i++){
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("title", items.get(i).getTitle());
            item.put("link", items.get(i).getLink());
            itemsForList.add(item);
        }
        return itemsForList;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getWebMaster() {
        return webMaster;
    }
    public void setWebMaster(String webMaster) {
        this.webMaster = webMaster;
    }



}