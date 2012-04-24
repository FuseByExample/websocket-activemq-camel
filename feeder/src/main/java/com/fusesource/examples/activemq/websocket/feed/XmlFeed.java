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
import com.fusesource.examples.activemq.websocket.marketdata.Stock;

import javax.jms.Message;
import javax.jms.Session;

/**
 * @author kmccormack
 */
public class XmlFeed {

    public static void main(String args[]) {
        XmlFeed feed = new XmlFeed();
        feed.start();
    }

    private static FeedThread thread = new FeedThread() {
        @Override
        Message createStockMessage(Session session, Stock updatedStock) throws Exception {
            String msgText = new StringBuffer()
                    .append("<stock>")
                    .append("<symbol>" + updatedStock.getSymbol() + "</symbol>")
                    .append("<name>" + updatedStock.getName() + "</name>")
                    .append("<low>" + updatedStock.getLow() + "</low>")
                    .append("<high>" + updatedStock.getHigh() + "</high>")
                    .append("<open>" + updatedStock.getOpen() + "</open>")
                    .append("<last>" + updatedStock.getLast() + "</last>")
                    .append("<change>" + updatedStock.getLast() + "</change>")
                    .append("</stock>")
                    .toString();
            return session.createTextMessage(msgText);
        }

        @Override
        Message createNewsMessage(Session session, News news) throws Exception {
            String msgText = new StringBuffer()
                    .append("<item>")
                    .append("<symbol>" + news.getSymbol() + "</symbol>")
                    .append("<title>" + news.getTitle() + "</title>")
                    .append("<picture>" + news.getTitle() + "</picture>")
                    .append("<info>" + news.getInfo() + "</info>")
                    .append("</item>")
                    .toString();
            return session.createTextMessage(msgText);
        }

    };

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.running = false;
        thread = null;
    }
}
