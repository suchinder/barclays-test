package main.dev;

public class TicketRequestObject {
	
	public TicketRequestObject(String partyName, int ticketCount) {
		super();
		this.partyName = partyName;
		this.ticketCount = ticketCount;
	}
	
	public TicketRequestObject() {
		// TODO Auto-generated constructor stub
	}

	private String partyName;
	private int ticketCount=0;
	
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public int getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}
	
}
