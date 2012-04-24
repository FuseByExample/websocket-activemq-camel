package com.fusesource.examples.activemq.websocket.feed;

/**
 * Copyright 2011 FuseSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.fusesource.examples.activemq.websocket.marketdata.News;
import com.fusesource.examples.activemq.websocket.marketdata.Portfolio;
import com.fusesource.examples.activemq.websocket.marketdata.Stock;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author kmccormack
 */
public abstract class FeedThread extends Thread {

    public boolean running = true;
    private Random random;

    abstract Message createStockMessage(Session session, Stock updatedStock) throws Exception;

    abstract Message createNewsMessage(Session session, News news) throws Exception;

    public void run() {
        try {

            // create the connection factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection("guest", "password");
            //Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create the session and topic
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic quoteTopic = session.createTopic("stockQuoteTopic");
            Topic newsTopic = session.createTopic("newsTopic");
            MessageProducer quotesProducer = session.createProducer(quoteTopic);
            MessageProducer newsProducer = session.createProducer(newsTopic);
            quotesProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            newsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // load in the portfolio of stocks
            Portfolio portfolio = new Portfolio();
            List<Stock> stocksList = portfolio.getStocks();
            List<News> newsList = portfolio.getNews();

            random = new Random();

            while (running) {

                Iterator<Stock> i = stocksList.iterator();
                while (i.hasNext()) {
                    Stock stock = i.next();
                    simulateChange(stock);
                    Message msg = createStockMessage(session, stock);
                    msg.setStringProperty("symbol", stock.getSymbol());
                    quotesProducer.send(msg);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }

                Iterator<News> ii = newsList.iterator();
                while (ii.hasNext()) {
                    News news = ii.next();
                    Message msg = createNewsMessage(session, news);
                    msg.setStringProperty("symbol", news.getSymbol());
                    newsProducer.send(msg);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Problem creating feed");
            ex.printStackTrace();
        }
    }

    private void simulateChange(Stock stock) {

        double maxChange = stock.getOpen() * 0.005;
        double change = maxChange - random.nextDouble() * maxChange * 2;
        stock.setChange(change);
        double last = stock.getLast() + change;

        if (last < stock.getOpen() + stock.getOpen() * 0.15 && last > stock.getOpen() - stock.getOpen() * 0.15) {
            stock.setLast(last);
        } else {
            stock.setLast(stock.getLast() - change);
        }

        if (stock.getLast() > stock.getHigh()) {
            stock.setHigh(stock.getLast());
        } else if (stock.getLast() < stock.getLow()) {
            stock.setLow(stock.getLast());
        }
        stock.setDate(new Date());

    }
}
