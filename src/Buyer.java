
import messaging.TextListenerImpl;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.*;

public class Buyer extends AbstractProducerConsumer{
	public void init()
	{
		
		Connection conn = null;
		Session session = null;
		Destination dest = null;
		TextMessage m = null;
		MessageConsumer mConsumer = null;
		dest = setDest("topic");
		
		if(dest!=null)
		{
			conn = createConnection();
		}
		
		if(conn!=null)
		{
			session = createSession(conn);
		}
		
		if(session!=null)
		{
			m = createMessage(session,dest);
			mConsumer = createConsumer(session, dest);
		}
		
		TextListenerImpl listener = new TextListenerImpl();
		try {
			mConsumer.setMessageListener(listener);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start()
	{
		char answer = 0;
		System.out.println("To end program, type Q or q, " + "then <return>");
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		while (!((answer == 'q') || (answer == 'Q'))) { 
		    try { 
		        answer = (char) inputStreamReader.read(); 
		    } catch (IOException e) { 
		        System.out.println("I/O exception: " + e.toString()); 
		    }
		}
	}
	
	public TextListenerImpl createListener(MessageListener listener)
	{
		return null;
		
	}
	
	protected MessageConsumer createConsumer(Session session, Destination dest)
	{
		try {
			MessageConsumer consumer = session.createConsumer(dest);
			return consumer;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
