package resource;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CreateTopicConnection {
	private InitialContext ctx = null;
	private TopicConnectionFactory f = null;
	private TopicConnection con = null;
	private TopicSession ses= null;
	private Topic t= null;
	
	public CreateTopicConnection()
	{
		try {
		ctx=new InitialContext();
		f = (TopicConnectionFactory)ctx.lookup("myTopicConnectionFactory");
		con =f.createTopicConnection(); 
		init();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void init() throws JMSException, NamingException
	{
		con.start();
        //2) create queue session  
       ses = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);  
        //3) get the Topic object  
        t = (Topic)ctx.lookup("myTopic");
	}
	
	public InitialContext getInitialContext()
	{
		return ctx;
		
	}
	
	public TopicConnectionFactory getTopicConnectionFactory()
	{
		return f;
		
	}
	
	public TopicConnection getTopicConnection()
	{
		return con;
		
	}
	
	public TopicSession getTopicSession()
	{
		return ses;
		
	}
	
	public Topic getTopic()
	{
		return t;
		
	}
	
}
