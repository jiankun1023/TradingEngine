/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package camelinaction;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;

public class FileCopierWithCamel {

	  public static void main(String args[]) throws Exception {
	        /*
	         *  Factory Pattern
	         *  TODO: Get the three Trading Engine;
	         * */
		  	TradingEngineFactory factory = new TradingEngineFactory();
		  	
		  	final TradingEngine ny = factory.getEngine("newyork");
			final TradingEngine tokyo = factory.getEngine("tokyo");
		  	final TradingEngine london = factory.getEngine("london");
		  	
		  	
		  	
		  	CamelContext tokyocontext = new DefaultCamelContext();
		  	CamelContext newYorkcontext = new DefaultCamelContext();
		  	CamelContext londoncontext = new DefaultCamelContext();
		  	
	        // connect to ActiveMQ JMS broker listening on localhost on port 61616
	        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	        
		  	
	        newYorkcontext.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));	        	       
	        tokyocontext.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
	        londoncontext.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
	       
	        
	        /**
	         *  New York Center Only cares about MSFT and ORCL
	         * */
	        newYorkcontext.addRoutes(new RouteBuilder() {
	            public void configure() {
	            	
	            	from("jms:topic:Final_Topic_MSFT")
	                .log("SUBSCRIBER RECEIVED: jms MSFT queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	ny.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(ny.report());
	                        System.out.println(ny.report()+" & Location: "+ny.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+ny.getName());

	                from("jms:topic:Final_Topic_ORCL")
	                .log("SUBSCRIBER RECEIVED: jms ORCL queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	ny.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(ny.report());
	                        System.out.println(ny.report()+" & Location: "+ ny.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+ ny.getName());

//	                from("jms:topic:Final_Topic_IBM")
//	                .log("SUBSCRIBER RECEIVED: jms IBM queue: ${body} from file: ${header.CamelFileNameOnly}")
//	                .process(new Processor(){
//	                    public void process(Exchange e) throws Exception{
//	                    	ny.update(e.getIn().getBody(String.class));
//	                        e.getIn().setBody(ny.report());
//	                        System.out.println(ny.report()+" & Location: "+ ny.getName());
//	                    }
//	                }).to("jms:queue:Final_Trading_Engine_"+ ny.getName());
	            }
	        });
	        
	        /**
	         *  Tokyo Center cares about MSFT, IBM and ORCL
	         * */
	        
	        tokyocontext.addRoutes(new RouteBuilder() {
	            public void configure() {
	            	
	            	from("jms:topic:Final_Topic_MSFT")
	                .log("SUBSCRIBER RECEIVED: jms MSFT queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	tokyo.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(tokyo.report());
	                        System.out.println(tokyo.report()+" & Location: "+tokyo.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+tokyo.getName());

	                from("jms:topic:Final_Topic_ORCL")
	                .log("SUBSCRIBER RECEIVED: jms ORCL queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	tokyo.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(tokyo.report());
	                        System.out.println(tokyo.report()+" & Location: "+ tokyo.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+ tokyo.getName());

	                from("jms:topic:Final_Topic_IBM")
	                .log("SUBSCRIBER RECEIVED: jms IBM queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	tokyo.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(tokyo.report());
	                        System.out.println(tokyo.report()+" & Location: "+ tokyo.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+ tokyo.getName());
	            }
	        });
	        
	        
	        /*
	         *  London Center cares about ORCL and IBM
	         * */
	        londoncontext.addRoutes(new RouteBuilder() {
	            public void configure() {
	            	
//	            	from("jms:topic:Final_Topic_MSFT")
//	                .log("SUBSCRIBER RECEIVED: jms MSFT queue: ${body} from file: ${header.CamelFileNameOnly}")
//	                .process(new Processor(){
//	                    public void process(Exchange e) throws Exception{
//	                    	london.update(e.getIn().getBody(String.class));
//	                        e.getIn().setBody(london.report());
//	                        System.out.println(london.report()+" & Location: ""+london.getName());
//	                    }
//	                }).to("jms:queue:Final_Trading_Engine_"+london.getName());

	                from("jms:topic:Final_Topic_ORCL")
	                .log("SUBSCRIBER RECEIVED: jms ORCL queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	london.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(london.report());
	                        System.out.println(london.report()+" & Location: "+ london.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+ london.getName());

	                from("jms:topic:Final_Topic_IBM")
	                .log("SUBSCRIBER RECEIVED: jms IBM queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .process(new Processor(){
	                    public void process(Exchange e) throws Exception{
	                    	london.update(e.getIn().getBody(String.class));
	                        e.getIn().setBody(london.report());
	                        System.out.println(london.report()+" & Location: "+ london.getName());
	                    }
	                }).to("jms:queue:Final_Trading_Engine_"+ london.getName());
	            }
	        });

	        // start the route and let it do its work
	        newYorkcontext.start();
	        tokyocontext.start();
	        londoncontext.start();
	        Thread.sleep(60000);

	        // stop the CamelContext
	        newYorkcontext.stop();
	        tokyocontext.stop();
	        londoncontext.stop();
	    }
}
