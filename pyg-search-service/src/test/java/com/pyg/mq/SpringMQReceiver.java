package com.pyg.mq;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Dave 发布订阅消息
 */
public class SpringMQReceiver {

	/*
	 * 发布订阅，和点对点
	 */
	@Test
	public void receiveMessage() throws Exception {

		// 加载spring配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext(
				"classpath*:spring/applicationContext-mq-consumer.xml");
		System.in.read();

	}

}
