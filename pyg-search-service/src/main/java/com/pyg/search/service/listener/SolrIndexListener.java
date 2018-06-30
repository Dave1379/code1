package com.pyg.search.service.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SolrIndexListener implements MessageListener {

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

			System.out.println("点对点模式接受消息:" + text);

		}
	}
}
