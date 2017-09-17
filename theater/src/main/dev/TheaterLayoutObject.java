package main.dev;


import java.util.ArrayList;

/**
 * Created by kssuc on 9/14/2017.
 */
public class TheaterLayoutObject {

    private ArrayList<Row> rows = new ArrayList<>();
    private int totalUnoccupiedSeats=0;
    
    public int getTotalUnoccupiedSeats() {
		return totalUnoccupiedSeats;
	}

	public void setTotalUnoccupiedSeats(int totalUnoccupiedSeats) {
		this.totalUnoccupiedSeats = totalUnoccupiedSeats;
	}

	public ArrayList<Row> getRows() {
        return this.rows;
      }
    
    public void setRows(ArrayList<Row> rows) {
    	  this.rows = rows;
    	}
    
    public void addRow(Row row) {
    	  this.rows.add(row);
    	}
}
