package main.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TheaterSeatingUtil {

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
	
	public void processRequests(TheaterLayoutObject theaterLayoutObject, ArrayList<TicketRequestObject> ticketRequests) {
		
		boolean ticketGranted = false;
        
		if(ticketRequests.isEmpty()) {
			throw new RuntimeException("Ticket requests are empty");
		}
		if(theaterLayoutObject == null) {
			throw new RuntimeException("Theater Layout is empty");
		}
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