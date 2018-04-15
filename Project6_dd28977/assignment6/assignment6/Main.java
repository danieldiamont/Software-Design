package assignment6;

import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
//		Theater t = new Theater(5,5, "bait");
//		
//		while(!t.seat_queue.isEmpty()) {
//			
//			System.out.println("Best seat: " + t.bestAvailableSeat().toString());
//			Seat seat = t.seat_queue.poll();
//			System.out.println(seat.toString());
//		}
		
//		Ticket lol = new Ticket("Ouijax","boxxx",new Seat(3,3),6);
//		
//		System.out.println(lol.toString());
		
		
		HashMap<String,Integer> boxOfficeList = new HashMap<String,Integer>();
		
		boxOfficeList.put("BX1", 10);
		boxOfficeList.put("BX2", 10);
		boxOfficeList.put("BX3", 10);
		boxOfficeList.put("BX4", 10);
		boxOfficeList.put("BX5", 10);
		
		
		Theater theater = new Theater(20,1,"Ouija");
		BookingClient bc = new BookingClient(boxOfficeList, theater);
		
		List<Thread> thread_list = bc.simulate();
		
		while(Thread.activeCount() > 1) {
		}
		
		if(!theater.hasTickets()) {
			System.out.print("Sorry, we are sold out!");
		}
//		List<Ticket> log = theater.getTransactionLog();
//		
//		System.out.println("\n\n\n\n------**** PRINTING LOG ****------");
//		System.out.println(log);
	}

}
