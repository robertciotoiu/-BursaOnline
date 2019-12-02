package old;
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
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.naming.NamingException;

public class Bidder extends AbstractProducerConsumer {

	public void init() throws NamingException {
		TopicConnection conn = null;
		TopicSession session = null;
		Destination dest = null;
		TextMessage m = null;
		MessageProducer mProducer = null;
		dest = setDest("topic");

		if (dest != null) {
			conn = createConnection();

		} else {
			System.out.println("Dest is null");
		}

		if (conn != null) {
			session = createTopicSession(conn);
		} else {
			System.out.println("Conn is null");
		}

		if (session != null) {
			m = createMessage(session, dest);
			mProducer = createProducer(session, dest);

		} else {
			System.out.println("Session is null");
		}

		if (m != null && mProducer != null) {
			try {
				sendMessage(mProducer, m);
			} catch (JMSException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (JMSException e) {
					}
				}
			}
		}
	}

	protected MessageProducer createProducer(Session session, Destination dest) {
		try {
			MessageProducer producer = session.createProducer(dest);
			return producer;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
