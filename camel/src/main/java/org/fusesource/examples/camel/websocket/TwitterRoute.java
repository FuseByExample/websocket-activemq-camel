package org.fusesource.examples.camel.websocket;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class TwitterRoute extends RouteBuilder {

    protected String consumerKey;
    protected String consumerSecret;
    protected String accessToken;
    protected String accessTokenSecret;

    public TwitterRoute() {
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
    }

    @Override
    public void configure() {

        from("twitter://search?type=polling&delay=5&keywords=java&" + getUriTokens()).routeId("fromTwittertoWebSocketTweet")
                .transform(body().convertToString())
                .delay(5000)
                .bean(TwitterRoute.class,"tweetToJSON")
                .log(LoggingLevel.INFO, ">> Search for FuseNews : ${body}")
                .to("websocket://0.0.0.0:9090/tweetTopic?sendToAll=true&staticResources=classpath:webapp");
    }

    protected String getUriTokens() {
        return "consumerKey=" + consumerKey + "&consumerSecret=" + consumerSecret + "&accessToken="
                + accessToken + "&accessTokenSecret=" + accessTokenSecret;
    }

    public String tweetToJSON(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"tweet\":\"");
        builder.append(message);
        builder.append("\"}");
        return builder.toString();
    }
}
