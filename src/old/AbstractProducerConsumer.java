package old;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
	
	protected TopicConnection createConnection() throws NamingException

	{
		try {
			 InitialContext ctx=new InitialContext();  
	            TopicConnectionFactory f=(TopicConnectionFactory)ctx.lookup("myTopicConnectionFactory");  
	            TopicConnection conn=f.createTopicConnection();  
	            conn.start();  
			return conn;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}

	protected TopicSession createTopicSession(TopicConnection conn)
	{
		try {
			TopicSession TopicSession = conn.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
			return TopicSession;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected TextMessage createMessage(TopicSession TopicSession, Destination dest)
	{
		try {
		
		TextMessage message = TopicSession.createTextMessage();
		
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
