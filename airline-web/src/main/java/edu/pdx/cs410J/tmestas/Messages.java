package edu.pdx.cs410J.tmestas;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * get message if a required parameter is missing
     * @param parameterName parameter
     * @return string with message
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * a message for when a flight has been added to an airline
     * @param airlineName airline name added
     * @param flightNumber flight added
     * @return string with message
     */
    public static String addedFlightToAirline(String airlineName, String flightNumber )
    {
        return String.format( "Added flight %s to %s", flightNumber, airlineName);
    }

    /**
     * get message if all dictionary entries are deleted
     * @return a message signifying if all dictionary entries are deleted
     */
    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

}
