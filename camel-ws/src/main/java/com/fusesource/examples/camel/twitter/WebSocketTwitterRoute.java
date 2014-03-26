package com.fusesource.examples.camel.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.fusesource.examples.camel.Helper;
import org.apache.camel.builder.RouteBuilder;

public class WebSocketTwitterRoute extends RouteBuilder {

    protected String consumerKey;
    protected String consumerSecret;
    protected String accessToken;
    protected String accessTokenSecret;
    protected String keywords;
    protected String delay;

/*    public WebSocketTwitterRoute() {
        URL url = getClass().getResource("/twitter-options.properties");

        InputStream inStream;
        try {
            inStream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalAccessError("twitter-options.properties could not be found");
        }

        Properties properties = new Properties();
        try {
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalAccessError("twitter-options.properties could not be found");
        }

        consumerKey = properties.get("consumer.key").toString();
        consumerSecret = properties.get("consumer.secret").toString();
        accessToken = properties.get("access.token").toString();
        accessTokenSecret = properties.get("access.token.secret").toString();
        this.keywords = properties.get("keywords").toString();
    }*/

    @Override
    public void configure() {

        from("twitter://search?type=polling&delay=" + delay + "&useSSL=true&keywords=" + keywords + "&" + getUriTokens())
                .routeId("fromTwittertoWebSocketTweet")
                .transform(body().convertToString())
                .delay(5000)
                .log(">> Results received : ${body}")
                .bean(Helper.class,"tweetToJSON")
                .log(">> Response prepared : ${body}")
                .to("websocket://0.0.0.0:9090/tweetTopic?sendToAll=true&staticResources=classpath:webapp");

    }

    protected String getUriTokens() {
        return "consumerKey=" + consumerKey + "&consumerSecret=" + consumerSecret + "&accessToken="
                + accessToken + "&accessTokenSecret=" + accessTokenSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
