package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kssuc on 9/14/2017.
 */
public class TheaterSeatingMain {

    public static void main( String[] args )
    {
    	TheaterLayoutObject theaterLayoutObject = new TheaterLayoutObject();
        ArrayList<TicketRequestObject> ticketRequests = new ArrayList<TicketRequestObject>();
        Row row;
        Section section;

        try{
            String fileName = "resources/Theater_input.txt";
            String theater_layout = "theater_layout";
            String ticket_requests = "ticket_requests";
            String nowReading = theater_layout;
            String nextLine = "";
            int rowNumber=1;
            

            File input = new File(fileName);
            BufferedReader b = new BufferedReader(new FileReader(input));

            while ((nextLine = b.readLine()) != null) {

                if(nextLine.trim().isEmpty()) {
                    nowReading = ticket_requests;
                    continue;
                }

                //parse the input file to read the theater layout
                if(nowReading.equalsIgnoreCase(theater_layout)){
                    String [] rowRecord = nextLine.split(" ");
                    row = new Row();
                    row.setRowNumber(rowNumber++);                    
                    for( int i = 0; i <= rowRecord.length - 1; i++)
                    {
                        section = new Section();
                        section.unoccupied_seats = Integer.parseInt(rowRecord[i]);
                        section.setSectionNumber(i+1);
                        row.addSection(section);
                        theaterLayoutObject.totalUnoccupiedSeats = section.unoccupied_seats + theaterLayoutObject.totalUnoccupiedSeats;
                    }
                    theaterLayoutObject.addRow(row);
                }else {
                	String [] rowRecord = nextLine.split(" ");
                	TicketRequestObject ticketRequest = new TicketRequestObject();
                	ticketRequest.setPartyName(rowRecord[0]);
                	ticketRequest.setTicketCount(Integer.parseInt(rowRecord[1]));
                	ticketRequests.add(ticketRequest);
                }
            }        
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
        	System.out.println("Invalid File ");
        }
        
        //Processing the request
        boolean ticketGranted = false;
        
        for (TicketRequestObject t : ticketRequests) {
			
			for (Row r : theaterLayoutObject.getRows()) {
				for (Section s : r.getSections()) {
					if(s.unoccupied_seats >= t.getTicketCount()) {
						System.out.println(t.getPartyName() + " Row " + r.getRowNumber() + " Section " + s.getSectionNumber());
						s.unoccupied_seats = s.unoccupied_seats - t.getTicketCount();
						theaterLayoutObject.totalUnoccupiedSeats = theaterLayoutObject.totalUnoccupiedSeats - t.getTicketCount();
						ticketGranted = true;
						break;
					}
				}
				if(ticketGranted) break;
			}
			if(!ticketGranted) {
				if(theaterLayoutObject.totalUnoccupiedSeats >= t.getTicketCount())
					System.out.println(t.getPartyName() + " Call to split party.");
				else
					System.out.println(t.getPartyName() + " Sorry, we can't handle your party.");
			}else
				ticketGranted = false;
		}
    }
}
