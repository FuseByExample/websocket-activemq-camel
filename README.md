# Example showing How to use WebSocket HTML 5 with JBoss A-MQ and Camel

## JBoss A-MQ

1) Download JBoss A-MQ (https://access.redhat.com/downloads/) and unzip/untar the project locally

2) Change users.properties file in ${fuse-amq.home}/etc directory and add a new 'guest' user where
   password is equal to 'password' and role 'admin'. This account will be used to authenticate the
   user connected from the web page with websocket activemq but also by the feed java application

   guest=password,admin

3) Copy the ActiveMQ configuration file from websocket-activemq-camel project to override activemq configuration
   used as default configuration by JBoss A-MQ. This config file will modify the transport connectors
   to add the websocket transport

   cp websocket-activemq-camel/feeder/src/main/config/fuseamq-websocket.xml ${fuse-amq.home}/etc/activemq.xml

4) Start JBoss a-mq using the shell or .bat script under bin directory
   bin/a-mq

         _ ____                                __  __  ____
        | |  _ \                    /\        |  \/  |/ __ \
        | | |_) | ___  ___ ___     /  \ ______| \  / | |  | |
    _   | |  _ < / _ \/ __/ __|   / /\ \______| |\/| | |  | |
   | |__| | |_) | (_) \__ \__ \  / ____ \     | |  | | |__| |
    \____/|____/ \___/|___/___/ /_/    \_\    |_|  |_|\___\_\

     JBoss A-MQ (6.0.0.redhat-009)
     http://fusesource.com/products/fuse-mq-enterprise/

   Hit '<tab>' for a list of available commands
   and '[cmd] --help' for help on a specific command.
   Hit '<ctrl-d>' or 'osgi:shutdown' to shutdown JBoss A-MQ.

   JBossA-MQ:karaf@root>

5) When the JBoss-AMQ console appears, install the activemq-websocket war file. This war file contains the
   web project and stomp javascript clients used to open communication between the web browser and websocket
   server running in JBoss A-MQ.

   JBossA-MQ:karaf@root>install -s war:mvn:com.fusesource.examples.activemq.websocket/web/1.0/war\?Webapp-Context=activemq-websocket

6) Checkout Websocket-activemq-camel git project from FuseByExample GitHub repository

   git co https://github.com/FuseByExample/websocket-activemq-camel.git
   cd websocket-activemq-camel
   git checkout fuse-amq

4)  Compile and start Feed application. This application will populate randomly data (stock prices) and publish
    them in a topic which is the topic used by websocket to expose the date to the web browser

    cd websocket-activemq-camel/feed
    mvn -P run-trader

5) Open your web browser and point to the following URL

    http://localhost:8181/activemq-websocket/stocks-activemq.html

6) Click on connect button

   Remark : The login to be used to connect the websocket client to the JBoss A-MQ server is guest & password is password

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