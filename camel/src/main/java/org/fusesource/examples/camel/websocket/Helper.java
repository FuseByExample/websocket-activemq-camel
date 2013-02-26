package org.fusesource.examples.camel.websocket;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

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

}
