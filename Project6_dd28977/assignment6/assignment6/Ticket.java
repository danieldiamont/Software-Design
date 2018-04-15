/* MULTITHREADING <Ticket.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

/**
 * This class represents a ticket to be printed once a customer has had a seat allocated
 * for him/her.
 * 
 * @author Daniel Diamont
 *
 */
public class Ticket {
	
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
