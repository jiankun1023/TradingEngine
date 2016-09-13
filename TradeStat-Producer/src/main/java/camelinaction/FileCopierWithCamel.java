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
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;

public class FileCopierWithCamel {

	  public static void main(String args[]) throws Exception {
	        // create CamelContext
	        CamelContext context = new DefaultCamelContext();

	        // connect to ActiveMQ JMS broker listening on localhost on port 61616
	        ConnectionFactory connectionFactory = 
	        	new ActiveMQConnectionFactory("tcp://localhost:61616");
	        context.addComponent("jms",
	            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
	        
	        // add our route to the CamelContext
	        context.addRoutes(new RouteBuilder() {
	            public void configure() {
	            	from("file:data/inbox?noop=true").log("RETRIEVED: ${file:name}").unmarshal().csv().split(body())
	            	.process(new Processor(){
	            		public void process(Exchange e) {
	            			System.out.println("MESSAGE FROM FILE: " 
	            					+ e.getIn().getHeader("CamelFileName") 
	            					+ " is heading to MPCS_51050_Final Queue for Stock: " 
	            					+ (e.getIn().getBody(String.class).split("\t"))[0].substring(1));
	            		}
	            	}).to("jms:queue:MPCS_51050_Final");
	                
	            	
	            	
	            	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	                from("file:data/outbox").to("jms:MPCS51050_config_test");
	            }
	        });

	        // start the route and let it do its work
	        context.start();
	        Thread.sleep(50000);

	        // stop the CamelContext
	        context.stop();
	    }
}
