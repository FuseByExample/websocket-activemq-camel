package com.fusesource.examples.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Helper {

    public static ConnectionFactory createConnectionFactory(String options) {

        String url = "tcp://localhost:61616";

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("guest","password",url);
        // optimize AMQ to be as fast as possible so unit testing is quicker
        connectionFactory.setCopyMessageOnSend(false);
        connectionFactory.setOptimizeAcknowledge(true);
        connectionFactory.setOptimizedMessageDispatch(true);

        return connectionFactory;
    }


    public static String tweetToJSON(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"tweet\":\"");
        builder.append(message);
        builder.append("\"}");
        return builder.toString();
    }

}
