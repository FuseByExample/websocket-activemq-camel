package com.fusesource.examples.activemq.websocket.marketdata;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
	
	public static void main(String[] args) {
		Portfolio stockFeed = new Portfolio(); 
		stockFeed.getStocks();
        stockFeed.getNews();
	}
	
	public List getStocks() {

		List list = new ArrayList();
		
        try {
        	String filePath = URLDecoder.decode(getClass().getClassLoader().getResource("portfolio.xml").getFile(), "UTF-8");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            Document doc = factory.newDocumentBuilder().parse(new File(filePath));
            NodeList stockNodes = doc.getElementsByTagName("stock");
            int length = stockNodes.getLength();
            Stock stock;
            Node stockNode;
            for (int i=0; i<length; i++) {
            	stockNode = stockNodes.item(i);
            	stock = new Stock();
            	stock.setSymbol( getStringValue(stockNode, "symbol") );
            	stock.setName( getStringValue(stockNode, "company") );
            	stock.setLast( getDoubleValue(stockNode, "last") );
            	stock.setHigh( stock.getLast() );
            	stock.setLow( stock.getLast() );
            	stock.setOpen( stock.getLast() );
            	stock.setChange( 0 );
            	list.add(stock);
            	System.out.println(stock.getSymbol());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return list;
	}
	
	public List getNews() {
        List list = new ArrayList();

        try {
            String filePath = URLDecoder.decode(getClass().getClassLoader().getResource("news.xml").getFile(), "UTF-8");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            Document doc = factory.newDocumentBuilder().parse(new File(filePath));
            NodeList newsNodes = doc.getElementsByTagName("item");
            int length = newsNodes.getLength();
            News news;
            Node newsNode;
            for (int i=0; i<length; i++) {
                newsNode = newsNodes.item(i);
                news = new News();
                news.setSymbol(getStringValue(newsNode, "symbol") );
                news.setTitle( getStringValue(newsNode, "title") );
                news.setPicture( getStringValue(newsNode, "picture") );
                news.setInfo( getStringValue(newsNode, "info") );
                list.add(news);
                System.out.println(news.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    private String getStringValue(Node node, String name) {
		return ((Element) node).getElementsByTagName(name).item(0).getFirstChild().getNodeValue();		
	}

	private double getDoubleValue(Node node, String name) {
		return Double.parseDouble( getStringValue(node, name) );		
	}

}