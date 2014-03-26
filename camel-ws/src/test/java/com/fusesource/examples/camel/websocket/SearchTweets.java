package com.fusesource.examples.camel.websocket;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SearchTweets {

    public static void main(String[] args) throws TwitterException {

        Twitter twitter = new TwitterFactory().getInstance();

        Query query = new Query();
        query.setQuery("jfj2013");
        query.setMaxId(5);
        query.setLang("en");
        query.setResultType("mixed");

        QueryResult result = null;

        /* Twitter Rest API 1.0
           Does not work anymore
        try {
            result = twitter.search(query);
            List<Tweet> tweets = result.getTweets();
            for (Tweet tweet : tweets) {
                System.out.println(">> @" + tweet.getFromUser() + " - " + tweet.getText());
            }
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        */

       // WORKS WITH Twitter4j 3.0.x
       // Tweeter API 1.1
        try {
            query = new Query("jfj2013");

            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);

            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
}