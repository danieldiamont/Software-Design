package assignment6;

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

	@Override
	public String toString() {
		return boxOfficeId;
		// TODO: Implement this method to return a string that resembles a ticket
	}

}
