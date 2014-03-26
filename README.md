# Example showing How to use WebSocket HTML5 with ActiveMQ, Camel, Karaf & JBoss Fuse technology

TODO

## ActiveMQ

1) Download ActiveMQ 5.8 from this location
    http://repo.fusesource.com/nexus/content/repositories/releases/org/apache/activemq/apache-activemq/5.8.0.redhat-60024/

or using Apache Release

    http://repo1.maven.org/maven2/org/apache/activemq/apache-activemq/5.8.0/

2) start Jetty Web Server

    cd websocket-activemq-camel/web
    mvn jetty:run

3)  Start ActiveMQ 5.x using the config provided in feeder/src/main/config directory

    cd ~/fuse/servers/apache-activemq-5.x/bin
    ./activemq console xbean:file:~/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/activemq-websocket.xml

4)  Compile and start Feed application

    cd websocket-activemq-camel/feeder
    mvn -P run-trader

5) Open your web browser at this address

    http://localhost:8080/stocks-activemq.html

and click on connect button

    Remark : To connect from the web page to the ActiveMQ broker, the login to be used is guest & password is password

## Camel

1) Start Apache Camel Routes (without using wss://)

    cd websocket-activemq-camel/camel-ws
    mvn camel:run

2) Compile and Start Feed application

    cd websocket-activemq-camel/feeder
    mvn -P run-trader

3) Verify stock and news websockets in your browser

    http://localhost:9090/stocks-camel.html
    http://localhost:9090/news-camel.html

    and click on connect button

To test SSL & wss:// protocol, execute the follownig command

1) Start Apache Camel Routes (with wss:// & HTTPS)

    cd websocket-activemq-camel/camel-ws-ssl
    mvn camel:run

2) Compile and Start Feed application

    cd websocket-activemq-camel/feeder
    mvn -P run-trader

3) Verify stock and news websockets in your browser

    https://localhost:8443/news-camel-wss.html

    and click on connect button


## JBoss A-MQ

1) Download JBoss A-MQ (https://access.redhat.com/downloads/) and unzip/untar the project locally

2) Copy ActiveMQ config file containing ActiveMQ WebSocket transports connectors

    cp ~/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/fuseamq-websocket.xml ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-024/etc/activemq.xml

 OR

    cp ~/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/org.fusesource.mq.fabric.server-default.cfg ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-024/etc
    cp ~/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/fuseamq-websocket.xml ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-024/etc/

3)) Add user guest and password password into the file etc/users.properties
 guest=password,admin

    cp /Users/chmoulli/Fuse/fuse-by-examples/websocket-activemq-camel/feeder/src/main/config/users.properties ~/Fuse/servers/jboss-a-mq-6.0.0.redhat-024/etc

4) Start JBoss A-MQ and install the web project

    install -s war:mvn:com.fusesource.examples.activemq.websocket/web/1.0/war\?Webapp-Context=activemq-websocket

5) Connect to the web site http://localhost:8181/activemq-websocket/stocks-activemq.html

## JBoss Fuse

! Twitter Demo does not work anymore with current Camel 2.10 release as Twitter API 1.0 has been retired
! In consequence Camel 2.12.3 is required and will proposed by JBoss Fuse 6.1

1) The project must be compiled using this profile option to generate the bundle(s) that we will deploy on the OSGI container

     mvn clean install -Pbundle

2) Next, download (https://repository.jboss.org/nexus/content/repositories/ea/org/jboss/fuse/jboss-fuse-full/6.1.0.redhat-375/)[JBoss Fuse] and unzip/untar the project locally

3) Move to the bin directory of the JBoss Fuse distribution and start the server

    ./fuse

4) Install features & bundles

When the console of Karaf appears, we will install the libraaris required to run the application. So first, install the XML resources file containing the definition about the modules to be deployed.
This features file contains for each module (= a feature), the bundles, configurations and parameters of the applications to be deployed and also references to others features/modules like camel, camel-twitter ...

    features:addurl mvn:com.fusesource.examples.websocket/features/1.0/xml/features
    features:install websocket-demo

The project can also be installed without using the features file created for this project but, in this case, using what is provisioned out of the box on JBoss Fuse platform.

    features:install camel-websocket
    features:install camel-twitter
    install -s mvn:com.fusesource.examples.activemq.websocket/camel-ws/1.0

5) Compile and Start Feed application

As the application generating the feeds for the websocket-stock server is not deployed on JBoss Fuse, it must be started separately using a maven command

    cd websocket-activemq-camel/feeder
    mvn -P run-trader

6) Connect to the web site using these addresses :

    http://localhost:9090/news-camel.html
    http://localhost:9090/stocks-camel.html
    http://localhost:9090/chat-camel.html


## Apache Karaf

1) Download Apache Karaf (http://karaf.apache.org/index/community/download.html) and unzip/untar the project locally

2) Install features & bundles

    features:addurl mvn:com.fusesource.examples.websocket/features/1.0/xml/features
    features:install websocket-demo

3) Compile and Start Feed application

    cd websocket-activemq-camel/feeder
    mvn -P run-trader

3) Connect to the web site using these addresses :

    http://localhost:9090/news-camel.html
    http://localhost:9090/stocks-camel.html
    http://localhost:9090/chat-camel.html
