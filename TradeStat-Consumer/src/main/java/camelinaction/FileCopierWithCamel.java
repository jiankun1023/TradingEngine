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

import java.text.DecimalFormat;

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
		  
		  	final TicData ticForIBM = new TicData("IBM");
		  	final TicData ticForMSFT = new TicData("MSFT");
		  	final TicData ticForORCL = new TicData("ORCL");
		  	final DecimalFormat formatter = new DecimalFormat("0.00");
		  	
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
	            	//from("file:data/inbox?noop=true").log("RETRIEVED: ${file:name}").unmarshal().csv().split(body()).to("jms:queue:MPCS_51050_LAB5");
	                from("jms:queue:MPCS_51050_Final").log("RECEIVED:  jms queue: ${body} from file: ${header.CamelFileNameOnly}")
	                .choice()
	                .when(body().regex(".*IBM.*")).process(new Processor(){
						@Override
						public void process(Exchange e) throws Exception {
							// TODO Auto-generated method stub
							ticForIBM.ticMsgProcess(e.getIn().getBody(String.class));
							StringBuilder sb = new StringBuilder();
							sb.append("IBM" + "\t");
							ticForIBM.setStrategy(new OperationVariance());
							sb.append(formatter.format(ticForIBM.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForIBM.getAskPrice()) + "\t");
							
							ticForIBM.setStrategy(new OperationMin());
							sb.append(formatter.format(ticForIBM.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForIBM.getAskPrice()) + "\t");
							
							ticForIBM.setStrategy(new OperationMean());
							sb.append(formatter.format(ticForIBM.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForIBM.getAskPrice()) + "\t");
							e.getIn().setBody(sb);
							System.out.println("IBM: " + sb.toString());
						}
	                }).to("jms:topic:Final_Topic_IBM")
	                
	                .when(body().regex(".*MSFT.*")).process(new Processor(){
						@Override
						public void process(Exchange e) throws Exception {
							// TODO Auto-generated method stub
							ticForMSFT.ticMsgProcess(e.getIn().getBody(String.class));
							StringBuilder sb = new StringBuilder();
							sb.append("MSFT" + "\t");
							ticForMSFT.setStrategy(new OperationVariance());
							sb.append(formatter.format(ticForMSFT.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForMSFT.getAskPrice()) + "\t");
							
							ticForMSFT.setStrategy(new OperationMin());
							sb.append(formatter.format(ticForMSFT.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForMSFT.getAskPrice()) + "\t");
							
							ticForMSFT.setStrategy(new OperationMean());
							sb.append(formatter.format(ticForMSFT.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForMSFT.getAskPrice()) + "\t");
							e.getIn().setBody(sb);
							System.out.println("MSFT: " + sb.toString());
						}
	                }).to("jms:topic:Final_Topic_MSFT")
	            	
	                .when(body().regex(".*ORCL.*")).process(new Processor(){
						@Override
						public void process(Exchange e) throws Exception {
							// TODO Auto-generated method stub
							ticForORCL.ticMsgProcess(e.getIn().getBody(String.class));
							StringBuilder sb = new StringBuilder();
							sb.append("ORCL" + "\t");
							ticForORCL.setStrategy(new OperationVariance());
							sb.append(formatter.format(ticForORCL.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForORCL.getAskPrice()) + "\t");
							
							ticForORCL.setStrategy(new OperationMin());
							sb.append(formatter.format(ticForORCL.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForORCL.getAskPrice()) + "\t");
							
							ticForORCL.setStrategy(new OperationMean());
							sb.append(formatter.format(ticForORCL.getBidPrice()) + "\t"); 
							sb.append(formatter.format(ticForORCL.getAskPrice()) + "\t");
							e.getIn().setBody(sb);
							System.out.println("ORCL: " + sb.toString());
						}
	                }).to("jms:topic:Final_Topic_ORCL").otherwise()
                	.to("jms:queue: Invalid_Data");
	                
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
