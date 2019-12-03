package messaging;

import java.util.ArrayList;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import resource.BuyerInterests;
import resource.CreateTopicConnection;

public class MyListener implements MessageListener {
	long ID = 0;
	ArrayList<BuyerInterests> interests = null;
	TopicPublisher publisher = null;
	TopicSession topicSession = null;
	public MyListener(ArrayList<BuyerInterests> interests, TopicPublisher publisher, TopicSession topicSession) {
		this.interests = interests;
		this.publisher = publisher;
		this.topicSession = topicSession;
		ID = System.nanoTime();
	}

	public void onMessage(Message m) {
		try {

			TextMessage msg = (TextMessage) m;
			if (msg.getJMSType().contentEquals("Request")) {
				for (BuyerInterests interest : interests) {

					long timeout = interest.getMaxAllowedOffertAge() + System.currentTimeMillis();
					if (interest.getCompany().contains(msg.getStringProperty("CompanyName"))
							&& (((msg.getDoubleProperty("StartPrice") >= interest.getMinPrice())
									&& (msg.getDoubleProperty("StartPrice") <= interest.getMaxPrice()))))
					// && ( timeout<= msg.getJMSExpiration())) //nu merge expiration-ul
					{

						try {
							TextMessage newmsg = topicSession.createTextMessage();

							Random r = new Random();
							System.out.println("Received messageID: " + msg.getJMSMessageID());
							// set default msg properties
							newmsg.setStringProperty("MsgID",msg.getStringProperty("MsgID"));
							newmsg.setStringProperty("CompanyName", interest.getCompany());
							newmsg.setDoubleProperty("StartPrice", msg.getDoubleProperty("StartPrice"));
							newmsg.setJMSExpiration(msg.getJMSExpiration());

							// simply add other properties
							newmsg.setJMSType("Response");
							System.out.println(msg.getJMSType());
							newmsg.setDoubleProperty("BuyerID", ID);
							newmsg.setDoubleProperty("OfferedPrice",
									msg.getDoubleProperty("StartPrice") + r.nextInt(10));
							newmsg.setLongProperty("InterestDate", System.currentTimeMillis());

							// 7) send message
							publisher.publish(newmsg);
						} catch (Exception e) {
							System.out.println(e);
						}

						// send back to bidder an offer
					} else {
						System.out.println("Company: " + interest.getCompany() + " ,minPrice: " + interest.getMinPrice()
								+ " ,maxPrice: " + interest.getMaxPrice());
						System.out.println("Timeout: " + timeout + " ,JMSExpiration:" + msg.getJMSExpiration());
					}
				}
			}
		} catch (JMSException e) {
			System.out.println(e);
		}

	}
}
