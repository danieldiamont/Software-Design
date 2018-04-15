/* MULTITHREADING <Seat.java>
 * EE422C Project 6 submission by
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

/**
 * This class represents a single seat in the theater.
 * @author Daniel Diamont
 *
 */
public class Seat {
	
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