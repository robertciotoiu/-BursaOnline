package messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TextListenerImpl implements MessageListener{
	public void onMessage(Message message) { 
	    TextMessage msg = null; 
	    try { 
	        if (message instanceof TextMessage) { 
	            msg = (TextMessage) message; 
	             System.out.println("Reading message: " + msg.getText()); 
	        } else { 
	             System.out.println("Message is not a " + "TextMessage"); 
	        } 
	    } catch (JMSException e) { 
	        System.out.println("JMSException in onMessage(): " + e.toString()); 
	    } catch (Throwable t) { 
	        System.out.println("Exception in onMessage():" + t.getMessage()); 
	    }
	}

}
