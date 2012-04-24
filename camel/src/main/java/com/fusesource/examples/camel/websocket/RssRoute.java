package com.fusesource.examples.camel.websocket;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.websocket.WebsocketComponent;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.jms.ConnectionFactory;

public class RssRoute extends RouteBuilder {
    
    private String serverFeed = "http://www.nytimes.com/services/xml/rss/nyt/GlobalHome.xml";

    @Override
    public void configure() throws Exception {

 /*       fromF("rss:%s?consumer.delay=2000", serverFeed)
           .bean(RssRoute.class,"convertRssToJSon")
           .log(">> RSS Feed received : ${body}")
           .to("websocket:newsTopic?sendToAll=true");*/
    }
    
    public String convertRssToJSon(String message) throws JSONException {

        JSONObject jsonObject = XML.toJSONObject(message);
        return jsonObject.toString();
    }
}

