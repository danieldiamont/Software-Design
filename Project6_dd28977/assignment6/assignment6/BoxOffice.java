/* MULTITHREADING <BoxOffice.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class represents a box office (a Runnable) that attempts to match its
 * clients to the best possible seat available.
 * 
 * Each BoxOffice uses locks to ensure atomic operation of getting the best 
 * available seat and assigning a ticket to the best available seat. Access
 * to ticket printing and the seat priority queue are also synchronized in
 * Theater.java
 * 
 * @author Daniel Diamont
 *
 */
public class BoxOffice implements Runnable{
	
	private int numPeople;
	private String boxOfficeId;
	private int counter = 1;
	private Theater theater;
	private Lock lock = new ReentrantLock();
	
	BoxOffice(String boxOfficeId, int numPeople, Theater theater){
		this.numPeople = numPeople;
		this.boxOfficeId = boxOfficeId;
		this.theater = theater;
	}

	@Override
	public void run() {
		
		//while there are people still in the line for this box office...
		while(counter <= numPeople) {
			
			if(theater.hasTickets()) { //if the theater has open seats...
				
				lock.lock();
				try {
					//attempt access to shared resource
					Seat bestSeat = theater.bestAvailableSeat();
					
					if(bestSeat != null) {
				
						theater.printTicket(boxOfficeId, bestSeat, counter);
						
						counter++;						
					}
				}
				finally {
					lock.unlock();
					//release the lock
					try {
						//sleep to allow other threads to pick up a time slice
						Thread.sleep(10);
					} catch (InterruptedException e) {
						System.out.println("The thread was somehow interrupted while sleeping...");
						e.printStackTrace();						
					}
				}				
			}
			else {
				//if the theater has no more open seats, kill this thread
				break;
			}			
		}
	}
}

