package main.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author kssuc
 *
 */
public class TheaterSeatingUtil {

	/**
	 * This method reads the input file and splits the data in the file into two ArrayLists - one for theater layout 
	 * and the other for ticket requests
	 * @param fileName
	 * @return HashMap<String, ArrayList<String>>
	 */
	public HashMap<String, ArrayList<String>> readFileAndSpitData(String fileName) {

		File input = null;
		FileReader fileReader = null;
		BufferedReader reader = null;
		String nextLine;
		String theater_layout = "theater_layout";
		String ticket_requests = "ticket_requests";
		String nowReading = theater_layout;

		HashMap<String, ArrayList<String>> result = null;
		ArrayList<String> layOut = new ArrayList<String>();
		ArrayList<String> ticketRequests = new ArrayList<String>();

		input = new File(fileName);
		try {
			fileReader = new FileReader(input);
			reader = new BufferedReader(fileReader);

			while ((nextLine = reader.readLine()) != null) {
				if(nextLine.trim().isEmpty()) {
					nowReading = ticket_requests;
					continue;
				}
				if(nowReading.equalsIgnoreCase(theater_layout))
					layOut.add(nextLine);
				else 
					ticketRequests.add(nextLine);				
			}
			result = new HashMap<String, ArrayList<String>>();
			result.put(theater_layout, layOut);
			result.put(ticket_requests, ticketRequests);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid File path");
		}finally
        {
            try{
            	if(reader !=null)
            	reader.close();
            	}catch(Exception e){
            		try{
            			if(fileReader != null)
            			fileReader.close();
            			}catch(Exception ee){
            				e.printStackTrace();
            			}
            }
        } 
		return result;

	}
	
	/**
	 * This method parses in the ArrayList and populates the data into TheaterLayOutObject
	 * @param layOut
	 * @return TheaterLayoutObject
	 */
	public TheaterLayoutObject populateLayOut(ArrayList<String> layOut) {
		
		TheaterLayoutObject result = null;
		Row row;
        Section section;
        int rowNumber=1;
        
        if(layOut.size() >= 1)
        	result = new TheaterLayoutObject();
        
		for(String nextLine : layOut) {

            String [] rowRecord = nextLine.split(" ");
            row = new Row();
            row.setRowNumber(rowNumber++);                    
            for( int i = 0; i <= rowRecord.length - 1; i++)
            {
                section = new Section();
                try {
					section.setUnoccupied_seats(Integer.parseInt(rowRecord[i]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("Seat count should be numeric");
				}
                section.setSectionNumber(i+1);
                row.addSection(section);
                result.setTotalUnoccupiedSeats(section.getUnoccupied_seats() + result.getTotalUnoccupiedSeats());
            }
            result.addRow(row);
        
		}
		return result;
	}
	
	/**
	 * This method parses in the ArrayList and populates the data into TheaterRequestObject
	 * @param ticketRequests
	 * @return ArrayList<TicketRequestObject>
	 */
	public ArrayList<TicketRequestObject> populateRequests(ArrayList<String> ticketRequests) {
		
		ArrayList<TicketRequestObject> result = null;
		
		if(ticketRequests.size() >= 1)
        	result = new ArrayList<TicketRequestObject>();
		for(String nextLine : ticketRequests) {
			String [] rowRecord = nextLine.split(" ");
			if(rowRecord.length != 2)
				throw new RuntimeException("Incorrect ticket request format");
			else {
				TicketRequestObject ticketRequest = new TicketRequestObject();
	        	ticketRequest.setPartyName(rowRecord[0]);
	        	try {
					ticketRequest.setTicketCount(Integer.parseInt(rowRecord[1]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("Incorrect ticket request format");
				}
	        	result.add(ticketRequest);
			}        	
		}
		return result;
	}
	
	/**
	 * This method accepts the theater layout and ticket requests as inputs and processes the requests
	 * @param theaterLayoutObject
	 * @param ticketRequests
	 */
	public void processRequests(TheaterLayoutObject theaterLayoutObject, ArrayList<TicketRequestObject> ticketRequests) {
		
		 
		if(ticketRequests.isEmpty()) {
			throw new RuntimeException("Ticket requests are empty");
		}
		if(theaterLayoutObject == null) {
			throw new RuntimeException("Theater Layout is empty");
		}
        for (TicketRequestObject t : ticketRequests) {        	
        	System.out.println(processAndGetResponse(theaterLayoutObject,t));        
		}
	}
	
	/**
	 * This method processes individual ticket request and returns the order response
	 * @param theaterLayoutObject
	 * @param ticketRequest
	 * @return String
	 */
	public String processAndGetResponse(TheaterLayoutObject theaterLayoutObject, TicketRequestObject ticketRequest) {
		
		String result = null;
		boolean ticketGranted = false;
		
		for (Row r : theaterLayoutObject.getRows()) {
			for (Section s : r.getSections()) {
				if(s.getUnoccupied_seats() >= ticketRequest.getTicketCount()) {
					result = ticketRequest.getPartyName() + " Row " + r.getRowNumber() + " Section " + s.getSectionNumber();
					s.setUnoccupied_seats(s.getUnoccupied_seats() - ticketRequest.getTicketCount());
					theaterLayoutObject.setTotalUnoccupiedSeats(theaterLayoutObject.getTotalUnoccupiedSeats() - ticketRequest.getTicketCount());
					ticketGranted = true;
					return result;
				}
			}
		}
		if(!ticketGranted) {
			if(theaterLayoutObject.getTotalUnoccupiedSeats() >= ticketRequest.getTicketCount())
				result = ticketRequest.getPartyName() + " Call to split party.";
			else
				result = ticketRequest.getPartyName() + " Sorry, we can't handle your party.";
		}
		return result;
	}
}
