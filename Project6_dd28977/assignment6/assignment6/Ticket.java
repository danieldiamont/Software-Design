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
