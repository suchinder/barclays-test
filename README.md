# barclays-test
Instructions for running the application:
 
 1) If not available, download Eclipse for J2EE developers
 2) Import the project downloaded from github link into your eclipse workspace. Choose File > Import > Existing projects into workspace. Navigate to the directory(barclays-test\theater) containing .project file and select that directory.
 3) Input file is located in the path : barclays-test\theater\resources. Modify the file Theater_input.txt and update your desired test data.
 4) Select the main java class barclays-test\theater\src\test\TheaterSeatingMain.java , right click and Run as Java Application. Output will be displayed in the standard output console.
 5) Run Junit Tests -
       a) Add Junit4 to builpath. Right click on the project -> properties -> Java Build Path -> Libraries -> Add Library -> Select JUnit -> Apply and Close
       b) Select TheaterSeatingTest.java -> Run As JUnit Test.
