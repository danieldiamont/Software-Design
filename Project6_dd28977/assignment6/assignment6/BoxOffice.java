package assignment6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
		
		while(counter <= numPeople) {
			
			if(theater.hasTickets()) {
				
				lock.lock();
				try {
					Seat bestSeat = theater.bestAvailableSeat();
					
					if(bestSeat != null) {
				
						theater.printTicket(boxOfficeId, bestSeat, counter);
						
						counter++;						
					}
				}
				finally {
					lock.unlock();
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			else {
				break;
			}
			
			
		}
	}
}

