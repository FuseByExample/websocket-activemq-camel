# Example showing How to use WebSocket HTML 5 with ActiveMQ and Camel

## ActiveMQ

1) Download ActiveMQ 5.6 snapshot from this location
    http://repo.fusesource.com/nexus/content/repositories/snapshots/org/apache/activemq/apache-activemq/5.6-fuse-SNAPSHOT/

    or Apache Release
    http://repo1.maven.org/maven2/org/apache/activemq/apache-activemq/5.6.0/

2) start Jetty Web Server

    cd websocket-activemq-camel/web
    mvn package jetty:run

3)  Start ActiveMQ 5.6 using the config provided in feeder/src/main/config directory
    cd ~/fuse/servers/apache-activemq-5.6/bin
    ./activemq console xbean:file:/Users/chmoulli/Fuse/examples/websocket-activemq-camel/feeder/src/main/config/activemq-websocket.xml

4)  Compile and start Feed application
    cd websocket-activemq-camel/feed
    mvn package -P run-trader

5) Open your web browser
    http://localhost:8080/stocks-activemq.html

    and click on connect button

## Camel

1) Start Apache Camel Routes (without using wss://)

    cd websocket-activemq-camel/camel
    mvn clean camel:run -P NO-SSL

2) Compile and Start Feed application

    cd websocket-activemq-camel/feed
    mvn clean package -P run-trader

3) Verify stock and news websockets in your browser

    http://localhost:8080/stocks-camel.html
    http://localhost:8080/news-camel.html

    and click on connect button

To test SSL & wss:// protocol, execute the follownig command

1) Start Apache Camel Routes (with wss:// & HTTPS)

    cd websocket-activemq-camel/camel
    mvn clean camel:run -P SSL

2) Compile and Start Feed application

    cd websocket-activemq-camel/feed
    mvn clean package -P run-trader

3) Verify stock and news websockets in your browser

    https://localhost:8443/news-camel-wss.html

    and click on connect button


