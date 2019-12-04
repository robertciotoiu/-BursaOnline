package resource;

import java.util.TimerTask;
import javax.jms.*;

import publisher.Bidder;

public class MyTimerTask extends TimerTask {

	Bidder bidder=null;
	long messageID;
	public MyTimerTask(Bidder bidder, long messageID2)
	{
		this.bidder=bidder;
		this.messageID=messageID2;
	}
	
	@Override
	public void run() {
		try {
			bidder.chooseOffer(messageID);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
