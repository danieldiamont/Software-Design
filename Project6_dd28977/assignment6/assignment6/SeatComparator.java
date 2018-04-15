/* MULTITHREADING <SeatComparator.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.Comparator;

/**
 * This is a helper class to build a priority queue of seats
 * @author Daniel Diamont
 *
 */
public class SeatComparator implements Comparator<Seat> {

	@Override
	public int compare(Seat a, Seat b) {
		if(a.getRowNum() < b.getRowNum()) return -1;
		else if(a.getRowNum() > b.getRowNum()) return 1;
		else if(a.getRowNum() == b.getRowNum()) {
			if(a.getSeatNum() < b.getSeatNum()) return -1;
			else return 1;
		}
		else {
			System.out.println("sorting error...");
			return 0;
		}
	}

}
