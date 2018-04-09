/* MULTITHREADING <Theater.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

public class Theater {
	
	private int numRows;
	private int seatsPerRow;
	private int totalNumSeats;
	private String show;
	PriorityQueue<Seat> seat_queue = new PriorityQueue<Seat>(new SeatComparator());
	List<Ticket> ticket_queue = new ArrayList<Ticket>();
	private int numSeatsAssigned;

	/**
	 * Constructor
	 * @param numRows
	 * @param seatsPerRow
	 * @param show
	 */
	public Theater(int numRows, int seatsPerRow, String show) {
		
		this.numRows = numRows;
		this.seatsPerRow = seatsPerRow;
		this.show = show;
		this.totalNumSeats = numRows*seatsPerRow;
		this.numSeatsAssigned = 0;
		
		//create list of seats to use for priority queue
		ArrayList<Seat> seat_list = new ArrayList<Seat>();
		
		//create the seats for the show
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < seatsPerRow; j++) {
				seat_queue.add(new Seat(i,j));
			}
		}
		
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		
		if(numSeatsAssigned == totalNumSeats) {
			return null;
		}
		
		return seat_queue.peek();
	
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		return null;
		//TODO: Implement this method
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		return ticket_queue;
	}
}
