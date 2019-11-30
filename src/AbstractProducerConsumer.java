import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class AbstractProducerConsumer {
	
	@Resource(mappedName="jms/ConnectionFactory")
	private static ConnectionFactory connectionFactory;
	public static final int NUM_MSGS=10;
	@Resource(mappedName="jms/Queue")protected static Queue queue;
	@Resource(mappedName="jms/Topic")protected static Topic topic;
	
	protected Destination setDest(String destType)
	{
		Destination dest = null;
		try { 
		    if (destType.equals("queue")) { 
		        dest = (Destination) queue; 
		    } else { 
		        dest = (Destination) topic; 
		    }
		    return dest;
		} 
		catch (Exception e) {
		    System.err.println("Error setting destination: " + e.toString()); 
		    e.printStackTrace(); 
		    System.exit(1);
		}
		return dest;
	}
	
	protected Connection createConnection()

	{
		try {
			Connection connection = connectionFactory.createConnection();
			return connection;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}

	protected Session createSession(Connection conn)
	{
		try {
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			return session;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected TextMessage createMessage(Session session, Destination dest)
	{
		try {
		
		TextMessage message = session.createTextMessage();
		
		return message;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	protected void sendMessage(MessageProducer producer, TextMessage message) throws JMSException
	{
		for (int i = 0; i < NUM_MSGS; i++) { 
		    message.setText("This is message " + (i + 1)); 
		    System.out.println("Sending message: " + message.getText()); 
		    producer.send(message);
		}
		
		producer.send(message);
		

	}

}
