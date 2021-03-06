package test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import main.dev.TheaterLayoutObject;
import main.dev.TheaterSeatingUtil;
import main.dev.TicketRequestObject;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TheaterSeatingTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private TheaterSeatingUtil theaterSeating;
	private TheaterLayoutObject theaterLayoutObject;
	private ArrayList<TicketRequestObject> ticketRequests;
   
	
	@Before
	public final void before() {
		theaterSeating = new TheaterSeatingUtil();		
	}
	
	@Test
	public void testReadFileAndSpitDataInvalidFile(){
		
		String fileName = "invalid_path/Theater_input.txt";
		
		exception.expect(RuntimeException.class);
		theaterSeating.readFileAndSpitData(fileName);

    }
	
	@Test
	public void testReadFileAndSpitDataResultNotNull(){
		
		String fileName = "resources/Theater_input.txt";
		HashMap<String, ArrayList<String>> result;
		result = theaterSeating.readFileAndSpitData(fileName);
		assertNotNull(result);

    }
	
	@Test
	public void testpopulateLayOutEmptyRequests() {

		ArrayList<String> testLayout = new ArrayList<String>();		
		assertNull(theaterSeating.populateLayOut(testLayout));
    }
	
	@Test
	public void testpopulateLayOutInValidFormat() {

		ArrayList<String> testLayout = new ArrayList<String>();
		testLayout.add("two 6 7 8");	

		exception.expect(RuntimeException.class);
		theaterLayoutObject = theaterSeating.populateLayOut(testLayout);   	
    }
	
	@Test
	public void testpopulateLayOutValidFormat() {

		ArrayList<String> testLayout = new ArrayList<String>();
		testLayout.add("5 7");	
		assertNotNull(theaterSeating.populateLayOut(testLayout));
    }
	
	@Test
	public void testpopulateRequestsInvalidRequestFormat() {

		ArrayList<String> testRequests = new ArrayList<String>();
		testRequests.add("John");
		
		exception.expect(RuntimeException.class);
    	ticketRequests = theaterSeating.populateRequests(testRequests);   	   	

    }
	
	@Test
	public void testpopulateRequestsValidRequestFormat() {

		ArrayList<String> testRequests = new ArrayList<String>();
		testRequests.add("John 3");
		testRequests.add("Cooper 6");
		
		assertNotNull(theaterSeating.populateRequests(testRequests));
    }
	
	@Test
	public void testpopulateRequestsEmptyRequests() {

		ArrayList<String> testRequests = new ArrayList<String>();		
		assertNull(theaterSeating.populateRequests(testRequests));
    }
	
	@Test
	public void testprocessRequestsEmptyRequests() {
		
		ArrayList<String> testLayout = new ArrayList<String>();
		testLayout.add("2 6 7 8");
		ArrayList<String> testRequests = new ArrayList<String>();
		
		theaterLayoutObject = theaterSeating.populateLayOut(testLayout);
    	ticketRequests = theaterSeating.populateRequests(testRequests); 
    	
    	exception.expect(RuntimeException.class);
    	theaterSeating.processRequests(theaterLayoutObject, ticketRequests);

    }
	
	@Test
	public void testprocessAndGetResponseExplanation1() {
		
		ArrayList<String> testLayout = new ArrayList<String>();
		testLayout.add("2 6 7 8");
		theaterLayoutObject = theaterSeating.populateLayOut(testLayout);
		TicketRequestObject request = new TicketRequestObject("Greg",2);		
		String response = theaterSeating.processAndGetResponse(theaterLayoutObject, request);
		
		assertEquals("Greg Row 1 Section 1",response);
	}
	
	@Test
	public void testprocessAndGetResponseExplanation2() {
		
		ArrayList<String> testLayout = new ArrayList<String>();
		testLayout.add("2 6 7 8");
		theaterLayoutObject = theaterSeating.populateLayOut(testLayout);
		TicketRequestObject request1 = new TicketRequestObject("Greg",2);
		String response1 = theaterSeating.processAndGetResponse(theaterLayoutObject, request1);
		TicketRequestObject request2 = new TicketRequestObject("Doug",9);
		String response2 = theaterSeating.processAndGetResponse(theaterLayoutObject, request2);
		
		assertEquals("Greg Row 1 Section 1",response1);
		assertEquals("Doug Call to split party.",response2);
	}
	
	@Test
	public void testprocessAndGetResponseExplanation3() {
		
		ArrayList<String> testLayout = new ArrayList<String>();
		testLayout.add("2 6 7 8");
		theaterLayoutObject = theaterSeating.populateLayOut(testLayout);
		TicketRequestObject request1 = new TicketRequestObject("Greg",2);
		String response1 = theaterSeating.processAndGetResponse(theaterLayoutObject, request1);
		TicketRequestObject request2 = new TicketRequestObject("Doug",9);
		String response2 = theaterSeating.processAndGetResponse(theaterLayoutObject, request2);
		TicketRequestObject request3 = new TicketRequestObject("Mike",30);
		String response3 = theaterSeating.processAndGetResponse(theaterLayoutObject, request3);
		
		assertEquals("Greg Row 1 Section 1",response1);
		assertEquals("Doug Call to split party.",response2);
		assertEquals("Mike Sorry, we can't handle your party.",response3);
	}

}
