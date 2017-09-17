package main.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kssuc on 9/14/2017.
 */
public class TheaterSeatingMain {

    public static void main( String[] args )
    {
    	
    	HashMap<String, ArrayList<String>> inputData;
    	TheaterLayoutObject theaterLayoutObject;
        ArrayList<TicketRequestObject> ticketRequests;
        String theater_layout = "theater_layout";
        String ticket_requests = "ticket_requests";
        String fileName = "resources/Theater_input.txt";
    	
        TheaterSeatingUtil theaterSeatingProcessor = new TheaterSeatingUtil();
    	
        // 1. Read the file and copy the data into ArrayLists 
    	inputData = theaterSeatingProcessor.readFileAndSpitData(fileName);
    	
    	// 2. Parse the ArrayList and populate the TheaterLayOutObject and TicketRequestObject
    	theaterLayoutObject = theaterSeatingProcessor.populateLayOut(inputData.get(theater_layout));
    	ticketRequests = theaterSeatingProcessor.populateRequests(inputData.get(ticket_requests));
    	
    	// 3. Process ticket requests and generate response
    	theaterSeatingProcessor.processRequests(theaterLayoutObject, ticketRequests);    	
    	
    }
}
