package messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import publisher.Bidder;

public class BidderListener implements MessageListener {
	
	ArrayList<String> messageIDs = null;
	Lock lock = new ReentrantLock();
	Bidder bidder = null;
	
	public BidderListener(Bidder bidder, ArrayList<String> messageIDs)
	{
		this.messageIDs = messageIDs;
		this.bidder = bidder;
	}
	
	public void onMessage(Message m) {
		try {
			

			TextMessage msg = (TextMessage) m;
			
			if(msg.getJMSType().contains("Response"))
			{
				//System.out.println("ORICE");
				for(String s:messageIDs)
				{
					System.out.print(s+", ");
				}
				System.out.println();
			if(messageIDs.contains(msg.getStringProperty("MsgID")))
			{
				//update the price and retain the interested buyer;
				System.out.println(msg.getDoubleProperty("OfferedPrice")+", "+msg.getDoubleProperty("StartPrice"));
				if(msg.getDoubleProperty("OfferedPrice")>=msg.getDoubleProperty("StartPrice"))
				{
					bidder.addOffer(msg);
							//System.out.println("Share: "+msg.getStringProperty("MsgID")+" ["+msg.getStringProperty("CompanyName")+"] has been sold to: "+msg.getDoubleProperty("BuyerID"));
							//messageIDs.remove(msg.getStringProperty("MsgID"));
				}
			}
			}
			else
			{
				System.out.println("MessageID: " +msg.getStringProperty("MsgID") +" not found.");
			}
		} catch (JMSException e) {
			System.out.println(e);
		}
	}
}
