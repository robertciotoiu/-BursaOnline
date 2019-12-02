package old;
import javax.naming.NamingException;

public class MainConsole {
	public static void main(String[] args)
	{
		Bidder bidder1 = new Bidder();
		
		try {
			bidder1.init();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
}
