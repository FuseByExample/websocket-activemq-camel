# Example showing How to use WebSocket HTML 5 with ActiveMQ and Camel

## ActiveMQ

1) Download ActiveMQ 5.6 snapshot from this location
    http://repo.fusesource.com/nexus/content/repositories/snapshots/org/apache/activemq/apache-activemq/5.6-fuse-SNAPSHOT/

2) start Jetty Web Server

    cd websocket-activemq-camel/web
    mvn clean package jetty:run

3)  Start ActiveMQ 5.6-SNAPSHOT using the config provided in feeder/src/main/config directory
    cd ~/fuse/servers/apache-activemq-5.6-fuse-SNAPSHOT/bin
    ./activemq console xbean:file:/Users/chmoulli/Fuse/examples/websocket-activemq-camel/feeder/src/main/config/activemq-websocket.xml

4)  Compile and start Feed application
    cd websocket-activemq-camel/feed
    mvn clean package -P run-trader

5) Open your web browser
    http://0.0.0.0:8161/trader-app-websocket/stocks-activemq.html

    and click on connect button

## Camel

1) Start Jetty Server

    cd websocket-activemq-camel/web
    mvn clean package jetty:run

2) Compile and Start Feed application

    cd websocket-activemq-camel/feed
    mvn clean package -P run-trader

3) Start Apache Camel Routes

    cd websocket-activemq-camel/camel
    vn clean camel:run

4) Verify stock and news websockets in your browser

    http://localhost:8080/stocks-camel.html
    http://localhost:8080/news-camel.html

    and click on connect button




