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
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Theater {
	
	private int numRows;
	private int seatsPerRow;
	private int totalNumSeats;
	private String show;
	
	PriorityQueue<Seat> seat_queue;
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
				seat_list.add(new Seat(i,j));
			}
		}
		
		seat_queue = new PriorityQueue<Seat>(totalNumSeats, new SeatComparator());
		
		for(Seat seat : seat_list) {
			seat_queue.add(seat);
		}
		
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public synchronized Seat bestAvailableSeat() {
		
		if(seat_queue.size() > 0) {
			return seat_queue.poll();
		}
		else return null;
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public synchronized Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		Ticket ticket = new Ticket(show,boxOfficeId,seat,client);
		
		numSeatsAssigned = numSeatsAssigned + 1;
		ticket_queue.add(ticket);
		
		System.out.println(ticket.toString());
		return ticket;
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		return ticket_queue;
	}
	
	public String getShow() {
		return show;
	}
	
	public boolean hasTickets() {
		if(numSeatsAssigned != totalNumSeats)
			return true;
		else
			return false;
	}
}
