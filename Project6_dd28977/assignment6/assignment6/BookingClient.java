/* MULTITHREADING <BookingClient.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.Thread;

public class BookingClient {
	
	HashMap<String,Integer> office;
	Theater theater;
  /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
  public BookingClient(Map<String, Integer> office, Theater theater) {
    // TODO: Implement this constructor
	  
	  this.office = (HashMap<String, Integer>) office;
	  this.theater = theater;
  }

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
	public List<Thread> simulate() {
		
		Set<String> boxNames = office.keySet();
		
		//thread list
		List<Thread> thread_list = new ArrayList<Thread>();
		
		//create a thread (box office) for each key in the set
		for(String box : boxNames) {
			Thread thread = new Thread(new BoxOffice(box, office.get(box),theater));
			thread.start();
			thread_list.add(thread);
		}
		
		return thread_list;
	}
}
