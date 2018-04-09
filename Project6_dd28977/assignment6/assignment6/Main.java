package assignment6;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Theater t = new Theater(5,5, "bait");
		
		while(!t.seat_queue.isEmpty()) {
			
			System.out.println("Best seat: " + t.bestAvailableSeat().toString());
			Seat seat = t.seat_queue.poll();
			System.out.println(seat.toString());
		}

	}

}
