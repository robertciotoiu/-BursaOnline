package publisher;



import java.util.ArrayList;

import javax.jms.*;

import messaging.BidderListener;
import resource.CreateTopicConnection;

public class Bidder implements Runnable {
	CreateTopicConnection topicConnection = null;
	TopicSubscriber receiver = null;
	TopicPublisher publisher = null;
	ArrayList<String> MessageIDs = new ArrayList<String>();
	
	public void createEventOffer(String companyName, double startPrice, long expirationTime)
	{
		// 5) create TextMessage object
		try {
					TextMessage msg = topicConnection.getTopicSession().createTextMessage();
					
					msg.setStringProperty("CompanyName", companyName);
					msg.setDoubleProperty("StartPrice", startPrice);
					msg.setJMSExpiration(System.currentTimeMillis() + expirationTime);
					msg.setJMSType("Request");
					
					long messageID = System.nanoTime();
					msg.setStringProperty("MsgID",String.valueOf(messageID));
					MessageIDs.add(String.valueOf(messageID));

					
					// 7) send message
					publisher.publish(msg);
				} catch (Exception e) {
					System.out.println(e);
				}
	}
	
	public void receiveEventOffer()
	{
		try {
			BidderListener listener=new BidderListener(MessageIDs); 
			receiver.setMessageListener(listener);
			
            //6) register the listener object with subscriber  
            
                          
            System.out.println("Publisher "+ Thread.currentThread().getId()+" is ready, waiting for messages...");  
            while(true){                  
                Thread.sleep(1000);  
            }  
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		topicConnection = new CreateTopicConnection();
		try {
			receiver = topicConnection.getTopicSession().createSubscriber(topicConnection.getTopic());
			publisher = topicConnection.getTopicSession().createPublisher(topicConnection.getTopic());
			
			receiveEventOffer();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
