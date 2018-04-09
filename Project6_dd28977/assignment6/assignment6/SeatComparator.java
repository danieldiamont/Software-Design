package assignment6;

import java.util.Comparator;

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
			System.out.println("we goofed");
			return 0;
		}
	}

}
