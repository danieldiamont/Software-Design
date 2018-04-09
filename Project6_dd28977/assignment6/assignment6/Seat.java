package assignment6;

public class Seat {
	
	private int rowNum;
	private int seatNum;

	public Seat(int rowNum, int seatNum) {
		this.rowNum = rowNum;
		this.seatNum = seatNum;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	@Override
	public String toString() {
		// TODO: Implement this method to return the full Seat location ex: A1
		String string = new String();
		
		string += String.valueOf(Character.toChars(0x41 + this.rowNum)) + this.seatNum; //0x32 is ASCII offset to 'A'
		return string;
	}

}
