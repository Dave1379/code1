package com.pyg.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

/**
 * 点对点模式：消息发送
 * 
 * @author Dave
 *
 */
public class TopicReceiver {

	@Test
	public void receiveMessage() throws Exception {

		String brokerURL = "tcp://192.168.66.66:61616";
		// 1创建mq连接工厂对象，连接消息服务器，指定actoveMQ连接地址，协议，ip
		ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
		// 从工厂中国获取连接
		Connection connection = cf.createConnection();
		// 开启连接
		connection.start();

		// 从连接对象中获取当前回话对象session
		// 参数1
		// 1,true : 必须使用activemQ事务提交模式来发送及接受消息。参数2必须是：Session.SESSION_TRANSACTED
		// 2,false：不使用activeMQ事务提交模式来接受消息。
		// 参数2：
		// AUTO_ACKNOWLEDGE:自动确定模式，满足异步效果
		// CLIENT_ACKNOWLEDGE:客户端确认模式

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 指定消息空间的地址
		 Topic topic = session.createTopic("myTopic");

		// 指定消费者，且指定消费空间地址
		MessageConsumer consumer = session.createConsumer(topic);

		// 使用监听模式消费消息
		consumer.setMessageListener(new MessageListener() { // 匿名内部类

			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				if (message instanceof TextMessage) {
					TextMessage m = (TextMessage) message;
					// 获取消息
					String text = null;
					try {
						text = m.getText();

					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("111111111111111111111111111111111111");
					System.out.println("订阅模式接收消息："+text);
				}
				System.out.println("2222222222222222222222");

			}
		});
		System.out.println("333333333333333333");
		
		System.in.read();

		// 发送消息
		consumer.close();
		session.close();
		connection.close();

	}
	
	
	
	

	@Test
	public void sendMessage1() throws Exception {
		
		String brokerURL = "tcp://192.168.66.66:61616";		
		//创建mq连接工厂对象，连接消息服务器，指定activeMQ连接地址：协议，ip地址，端口
		ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
		//从工厂中获取连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接对象中获取当前回话对象session
		//参数1：
		//1,true : 必须使用activemQ事务提交模式来发送及接受消息。参数2必须是：Session.SESSION_TRANSACTED
		//2,false：不使用activeMQ事务提交模式来接受消息。
		//参数2：
		//AUTO_ACKNOWLEDGE:自动确定模式，满足异步效果
		//CLIENT_ACKNOWLEDGE:客户端确认模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//指定消息空间地址
		Topic topic = session.createTopic("myTopic");
		
		//指定消息消费者，且指定消费消息空间地址
		MessageConsumer consumer = session.createConsumer(topic);
		
		//使用监听模式消费消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				if(message instanceof TextMessage) {
					
					TextMessage m = (TextMessage) message;
					//获取消息
					String text = null;
					try {
						text = m.getText();
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("点对点模式接受消息:"+text);
					
				}
			}
		});
		
		System.in.read();
		
		//资源关闭
		consumer.close();
		session.close();
		connection.close();
		
		
		
		

	}

	
	
	
	

}
