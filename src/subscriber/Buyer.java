package subscriber;
import java.util.ArrayList;

import javax.jms.*;
import javax.naming.InitialContext;

import messaging.MyListener;
import resource.BuyerInterests;
import resource.CreateTopicConnection;

public class Buyer implements Runnable{
	ArrayList<BuyerInterests> interests = new ArrayList<BuyerInterests>();
	CreateTopicConnection topicConnection = null;
	CreateTopicConnection topicConnectionReceiver = null;
	TopicSubscriber receiver = null;

	
	public void setInterests(String company, double minPrice, double maxPrice, long maxAllowedOffertAge)
	{
		BuyerInterests interest = new BuyerInterests();
		interest.setCompany(company);
		interest.setMinPrice(minPrice);
		interest.setMaxPrice(maxPrice);
		interest.setMaxAllowedOffertAge(maxAllowedOffertAge);
		
		interests.add(interest);
	}

	public void receiveEvent()
	{
		try { 
            MyListener listener=new MyListener(interests); 
              
            //6) register the listener object with subscriber  
            receiver.setMessageListener(listener);
                          
            System.out.println("Subscriber "+Thread.currentThread().getId() +" is ready, waiting for messages...");  
            //System.out.println("press Ctrl+c to shutdown...");  
            while(true){                  
                Thread.sleep(1000);  
            }  
        }catch(Exception e){System.out.println(e);}  
    }

	@Override
	public void run() {
		topicConnectionReceiver = new CreateTopicConnection();
		try {
			 //4)create TopicSubscriber
			receiver = topicConnectionReceiver.getTopicSession().createSubscriber(topicConnectionReceiver.getTopic());
			
			receiveEvent();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}  
}
