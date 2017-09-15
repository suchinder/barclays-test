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
        FileReader fileReader = null;
        BufferedReader reader = null;
        File input = null;

        /*Parse the input file and populate the theater layout and ticket request objects*/
        try{
            String fileName = "resources/Theater_input.txt";
            String theater_layout = "theater_layout";
            String ticket_requests = "ticket_requests";
            String nowReading = theater_layout;
            String nextLine = "";
            int rowNumber=1;
            

            input = new File(fileName);
            fileReader = new FileReader(input);
            reader = new BufferedReader(fileReader);

            while ((nextLine = reader.readLine()) != null) {

                if(nextLine.trim().isEmpty()) {
                    nowReading = ticket_requests;
                    continue;
                }

                //Parsing the theater layout.
                if(nowReading.equalsIgnoreCase(theater_layout)){
                    String [] rowRecord = nextLine.split(" ");
                    row = new Row();
                    row.setRowNumber(rowNumber++);                    
                    for( int i = 0; i <= rowRecord.length - 1; i++)
                    {
                        section = new Section();
                        section.setUnoccupied_seats(Integer.parseInt(rowRecord[i]));
                        section.setSectionNumber(i+1);
                        row.addSection(section);
                        theaterLayoutObject.setTotalUnoccupiedSeats(section.getUnoccupied_seats() + theaterLayoutObject.getTotalUnoccupiedSeats());
                    }
                    theaterLayoutObject.addRow(row);
                }
                //Parsing the ticket requests.
                else {
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
        	e.printStackTrace();
        }finally
        {
            try{
            	reader.close();
            	}catch(Exception e){
            		try{
            			fileReader.close();
            			}catch(Exception ee){
            				e.printStackTrace();
            			}
            }
        }
        
        //Processing the requests and printing confirmed requests or explanations
        boolean ticketGranted = false;
        
        for (TicketRequestObject t : ticketRequests) {
			
			for (Row r : theaterLayoutObject.getRows()) {
				for (Section s : r.getSections()) {
					if(s.getUnoccupied_seats() >= t.getTicketCount()) {
						System.out.println(t.getPartyName() + " Row " + r.getRowNumber() + " Section " + s.getSectionNumber());
						s.setUnoccupied_seats(s.getUnoccupied_seats() - t.getTicketCount());
						theaterLayoutObject.setTotalUnoccupiedSeats(theaterLayoutObject.getTotalUnoccupiedSeats() - t.getTicketCount());
						ticketGranted = true;
						break;
					}
				}
				if(ticketGranted) break;
			}
			if(!ticketGranted) {
				if(theaterLayoutObject.getTotalUnoccupiedSeats() >= t.getTicketCount())
					System.out.println(t.getPartyName() + " Call to split party.");
				else
					System.out.println(t.getPartyName() + " Sorry, we can't handle your party.");
			}else
				ticketGranted = false;
		}
    }
}
