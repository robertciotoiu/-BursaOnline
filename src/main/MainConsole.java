package main;

import publisher.Bidder;
import subscriber.Buyer;

public class MainConsole {

	public static void main(String[] args) {

		setup();

//		// 6) write message
//		try {
//		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
//		while (true) {
//			System.out.println("Enter Msg, end to terminate:");
//			String s = b.readLine();
//			if (s.equals("end"))
//				break;
//
//			System.out.println("Message successfully sent.");
//		}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	}

	public static void setup() {
		
		Buyer buyer = new Buyer();
		Buyer buyer2 = new Buyer();
		Buyer buyer3 = new Buyer();
		
		Thread bu1 = new Thread(buyer);
		Thread bu2 = new Thread(buyer2);
		Thread bu3 = new Thread(buyer3);
		
		bu1.start();
		bu2.start();
		bu3.start();
		
		Bidder bidder = new Bidder();
		Thread bi1 = new Thread(bidder);
		bi1.start();
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buyer.setInterests("Apple", 0.0, 250.0, 30000);
		buyer.setInterests("Microsoft", 0.0, 250.0, 30000);

		buyer2.setInterests("Apple", 0.0, 250.0, 30000);
		buyer2.setInterests("Microsoft", 0.0, 250.0, 30000);

		buyer3.setInterests("Apple", 0.0, 250.0, 30000);
		buyer3.setInterests("Microsoft", 0.0, 250.0, 30000);

		bidder.createEventOffer("Apple", 200.0, 30000);// 30000 millis = 30 seconds
		bidder.createEventOffer("Apple", 251.0, 30000);// 30000 millis = 30 seconds
		bidder.createEventOffer("Microsoft", 197.0, 30000);// 30000 millis = 30 seconds
	}
}
