
Name: Tanner Mestas
Assignment: Advanced Programming with Java - Project 5

The purpose of this assignment is to provide a command line interface that can make http requests to
a web server. These requests can add and display airlines and flights

*****************************************************************************************************************

Options:

-README                                     | Prints readme file with project information
-host [hostname]                            | Specifies web server host
-port [port]                                | Specifies web server port
-search "Airline Name"                      | Displays all flights in an existing airline
-search "Airline Name" source destination   | Displays all flights between 2 specified airports in existing airline

*****************************************************************************************************************

Usage:

To add flight:
java Project5 -host [hostname] -port [port] "Airline Name" flightNum source departDate departTime destination \
arriveDate arriveTime

To display all of an airlines flights:
java Project5 -host [hostname] -port [port] "Airline Name"

To display an airlines flights by airport:
java Project5 -host [hostname] -port [port] "Airline Name" source destination

Flight Number: Must be a parseable integer
Source Airport Code: Must be a 3-letter code that corresponds to a stored airport
Departure Date: Date the flight will depart in format dd/mm/yyyy
Departure Time: Time the flight will depart in format hh:mm a
Destination Airport Code: Must be a 3-letter code that corresponds to a stored airport
Arrival Date: Date the flight will arrive in format dd/mm/yyyy
Arrival Time: Time the flight will arrive in format hh:mm a
