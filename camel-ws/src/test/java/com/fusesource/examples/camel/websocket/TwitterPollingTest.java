package com.fusesource.examples.camel.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterPollingTest extends CamelTestSupport {

    private static final transient Logger LOG = LoggerFactory.getLogger(TwitterPollingTest.class);

    protected String consumerKey;
    protected String consumerSecret;
    protected String accessToken;
    protected String accessTokenSecret;
    protected String keywords;

    public TwitterPollingTest() {
        config();
    }

    public void config() {

        URL url = TwitterPollingTest.class.getResource("/twitter-options.properties");

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

        this.consumerKey = properties.get("consumer.key").toString();
        this.consumerSecret = properties.get("consumer.secret").toString();
        this.accessToken = properties.get("access.token").toString();
        this.accessTokenSecret = properties.get("access.token.secret").toString();
        this.keywords = properties.get("keywords").toString();
    }


    protected String getUriTokens() {
        return "consumerKey=" + consumerKey + "&consumerSecret=" + consumerSecret + "&accessToken="
                + accessToken + "&accessTokenSecret=" + accessTokenSecret;
    }

    @Test
    public void testSearchTimeline() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.assertIsSatisfied();
        List<Exchange> tweets = mock.getExchanges();
        if (LOG.isInfoEnabled()) {
            for (Exchange e : tweets) {
                LOG.info("Tweet: " + e.getIn().getBody(String.class));
            }
        }
    }

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("twitter://search?type=polling&useSSL=true&keywords=" + keywords + "&" + getUriTokens())
                   .transform(body().convertToString()).to("mock:result");
            }
        };
    }

}
