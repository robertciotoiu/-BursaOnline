package messaging;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class BidderListener implements MessageListener {
	
	ArrayList<String> messageIDs = null;
	
	public BidderListener(ArrayList<String> messageIDs)
	{
		this.messageIDs = messageIDs;
	}
	
	public void onMessage(Message m) {
		try {

			TextMessage msg = (TextMessage) m;
			
			if(msg.getJMSType().contains("Response"))
			{
			if(messageIDs.contains(msg.getJMSMessageID()))
			{
				//update the price and retain the interested buyer;
				if(msg.getDoubleProperty("OfferedPrice")>=msg.getDoubleProperty("StartPrice"))
				{
					System.out.println("Share: "+msg.getJMSMessageID()+" ["+msg.getStringProperty("CompanyName")+"] has been sold to: "+msg.getDoubleProperty("BuyerID"));
					Thread.currentThread().interrupt();
				}
			}
			else
			{
				System.out.println("MessageID: " +msg.getJMSMessageID() +" not found.");
			}
			}
		} catch (JMSException e) {
			System.out.println(e);
		}
	}
}
