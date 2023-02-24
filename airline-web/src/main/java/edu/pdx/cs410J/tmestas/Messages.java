package edu.pdx.cs410J.tmestas;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String addedFlightToAirline(String airlineName, String flightNumber )
    {
        return String.format( "Added flight %s to %s", flightNumber, airlineName);
    }

    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

}
