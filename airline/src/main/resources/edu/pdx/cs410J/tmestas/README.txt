
Name: Tanner Mestas
Assignment: Advanced Programming with Java - Project 4

The purpose of this assignment is to provide a command line interface for a user to add flight objects to
airline objects, and perform different actions with the data.

*****************************************************************************************************************

Options:

-README                 | Prints readme file with project information
-textFile <FilePath>    | Prints the provided flight and airline to a text file (if valid), appends to file
                        | if there already exists a file, aborts if airlines in file and command line differ
-pretty <FilePath>      | Pretty prints airline and its flights to a text file
-pretty -               | Pretty prints airline and its flights to standard out
-pretty <FilePath> -    | Pretty prints airline and its flights to a text file and standard out
-XmlFile <FilePath>     | Prints the provided flight and airline to a Xml file (if valid), appends to file
                        | if there already exists a file, aborts if airlines in file and command line differ

*Note*: -textFile and -XmlFile options can not be used at the same time.

*****************************************************************************************************************

Args (in order):

Flight Number: Must be a parseable integer
Source Airport Code: Must be a 3-letter code that corresponds to a stored airport
Departure Date: Date the flight will depart in format dd/mm/yyyy
Departure Time: Time the flight will depart in format hh:mm a
Destination Airport Code: Must be a 3-letter code that corresponds to a stored airport
Arrival Date: Date the flight will arrive in format dd/mm/yyyy
Arrival Time: Time the flight will arrive in format hh:mm a

*****************************************************************************************************************

Command Line Usage:

java -jar target/airline-2023.0.0.jar [options] <args>

*****************************************************************************************************************