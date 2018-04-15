/* MULTITHREADING <Main.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.HashMap;
import java.util.List;

/**
 * This is the driver for the multithreading project. It creates the box offices, the theater, and
 * begins the booking client simulation across multiple threads (one per box office).
 * @author Daniel Diamont
 *
 */
public class Main {

	public static void main(String[] args) {
		
		//hashmap to tie box offices and initial waiting line together
		HashMap<String,Integer> boxOfficeList = new HashMap<String,Integer>();
		
		boxOfficeList.put("BX1", 100);
		boxOfficeList.put("BX2", 100);
		boxOfficeList.put("BX3", 100);
		boxOfficeList.put("BX4", 100);
		boxOfficeList.put("BX5", 100);
		boxOfficeList.put("BX6", 100);
		boxOfficeList.put("BX7", 100);
		boxOfficeList.put("BX8", 100);
		boxOfficeList.put("BX9", 100);
		boxOfficeList.put("BX10", 100);
		boxOfficeList.put("BX11", 100);
		
		//create theater
		Theater theater = new Theater(2000,1,"Ouija");
		
		//pass the theater and the box office to the booking client
		BookingClient bc = new BookingClient(boxOfficeList, theater);
		
		//begin client simulation
		List<Thread> thread_list = bc.simulate();
		
		//wait for all other active threads to finish
		while(Thread.activeCount() > 1) {
		}
		
		//if there are no more tickets and there are still people remaining in the queue,
		//print sold out message
		if(!theater.hasTickets()) {
			System.out.print("Sorry, we are sold out!");
		}
		
		System.out.println("thread list : " + thread_list);

	}

}
