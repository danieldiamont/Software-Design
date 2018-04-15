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
	
	private PriorityQueue<Seat> seat_queue;
	private List<Ticket> ticket_queue = new ArrayList<Ticket>();
	private int numSeatsAssigned;

	/**
	 * Constructor for theater
	 * 
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
			for(int j = 1; j <= seatsPerRow; j++) {
				seat_list.add(new Seat(i,j));
			}
		}
		
		//allocate a priority queue sorted by the seat comparator
		seat_queue = new PriorityQueue<Seat>(totalNumSeats, new SeatComparator());
		
		//add all the seats to the priority queue
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
		
		//create a ticket
		Ticket ticket = new Ticket(show,boxOfficeId,seat,client);
		
		//increased the number of assigned seats in the theater
		numSeatsAssigned = numSeatsAssigned + 1;
		
		//add ticket to the transaction log
		ticket_queue.add(ticket);
		
		//print out ticket to console
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
	
	/**
	 * 
	 * @return true if the theater is not full; return false otherwise
	 */
	public boolean hasTickets() {
		if(numSeatsAssigned != totalNumSeats)
			return true;
		else
			return false;
	}

	static public class Seat {
	
		private int rowNum;
		private int seatNum;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		/*
		 * GETTERS AND SETTERS
		 */
		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		/**
		 * toString method
		 * 
		 * @return string holds the converted rowNumber into alphabetized representation
		 * 
		 * e.g.:
		 * 
		 * 0  -> A
		 * 26 -> AA
		 * 27 -> AB
		 * .
		 * .
		 * .
		 * 
		 */
		@Override
		public String toString() {
			String string = new String();

			string += rowString(this.rowNum) + this.seatNum; //0x41 is the ASCII offset to 'A'
			return string;
		}
		
		/*
		 * Recursive helper function for toString method
		 */
		public String rowString(int num) {
			if(num < 0) {
				return "";
			}
			else {
				return rowString((num / 26) - 1) + (char)(0x41 + num % 26);
			}
		}

	}

	static public class Ticket {
	
		private String show;
		private String boxOfficeId;
		private Seat seat;
		private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		/*
		 * GETTERS
		 */
		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		/**
		 * @return out is a string containing the text display of a ticket in the movie theater
		 */
		@Override
		public String toString() { 
			String out = new String();
			out += "-------------------------------\n";
			out +="| Show: " + this.show + "\n|\n";
			out +="| Box Office ID: " + this.boxOfficeId + "\n|\n";
			out +="| Seat: " + this.seat + "\n|\n";
			out +="| Client: " + this.client + "\n|\n";		
			out +="-------------------------------\n";
			
			return out;
			
		}

	}
}
