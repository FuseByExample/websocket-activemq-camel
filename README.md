# Example showing How to use WebSocket HTML 5 with ActiveMQ and Camel

## ActiveMQ

1) Download ActiveMQ 5.7 from this location
    http://repo.fusesource.com/nexus/content/repositories/releases/org/apache/activemq/apache-activemq/5.7.0.fuse-71-047/

    or Apache Release
    http://repo1.maven.org/maven2/org/apache/activemq/apache-activemq/5.7.0/

2) start Jetty Web Server

    cd websocket-activemq-camel/web
    mvn jetty:run

3)  Start ActiveMQ 5.x using the config provided in feeder/src/main/config directory
    cd ~/fuse/servers/apache-activemq-5.x/bin
    ./activemq console xbean:file:/Users/chmoulli/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/activemq-websocket.xml

4)  Compile and start Feed application
    cd websocket-activemq-camel/feed
    mvn -P run-trader

5) Open your web browser
    http://localhost:8080/stocks-activemq.html

    and click on connect button
    Remark : To connect from the web page to the ActiveMQ broker, the login to be used is guest & password is password

## Camel

1) Start Apache Camel Routes (without using wss://)

    cd websocket-activemq-camel/camel
    mvn camel:run -P NO-SSL

2) Compile and Start Feed application

    cd websocket-activemq-camel/feed
    mvn -P run-trader

3) Verify stock and news websockets in your browser

    http://localhost:9090/stocks-camel.html
    http://localhost:9090/news-camel.html

    and click on connect button

To test SSL & wss:// protocol, execute the follownig command

1) Start Apache Camel Routes (with wss:// & HTTPS)

    cd websocket-activemq-camel/camel
    mvn camel:run -P SSL

2) Compile and Start Feed application

    cd websocket-activemq-camel/feed
    mvn -P run-trader

3) Verify stock and news websockets in your browser

    https://localhost:8443/news-camel-wss.html

    and click on connect button


 FOR FUSE-MQ

 1) Copy config file containing ActiveMQ WebSocket transports connectors

 cp /Users/chmoulli/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/org.fusesource.mq.fabric.server-default.cfg ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-009/etc
 cp /Users/chmoulli/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/fuseamq-websocket.xml ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-009/etc

OR

cp /Users/chmoulli/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/fuseamq-websocket.xml ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-009/etc/activemq.xml

 2) Add user guest and password password into the file etc/users.properties
 guestpassword,admin

 cp /websocket-activemq-camel/feeder/src/main/config/users.properties ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-009/etc

 3) Start JBoss A-MQ and install the web project
 install -s war:mvn:com.fusesource.examples.activemq.websocket/web/1.0/war\?Webapp-Context=activemq-websocket

 3) Connect to the web site http://localhost:8181/activemq-websocket/stocks-activemq.html

