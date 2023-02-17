package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;

public class Converter {
    public static void main(String[] args){

        //String textFilePath = args[0];
        //String xmlFilePath = args[1];

        String textFilePath = "C:\\Users\\tanne\\Desktop\\seipp.txt";
        String xmlFilePath = "C:\\Users\\tanne\\Desktop\\seipp.xml";
        Airline newAirline;

        try {
            FileReader f = new FileReader(textFilePath);
            BufferedReader b = new BufferedReader(f);
            TextParser parser = new TextParser(b);
            XmlDumper dumper = new XmlDumper(xmlFilePath);
            newAirline = parser.parse();
            Collections.sort(newAirline.getFlights());
            dumper.dump(newAirline);
        }catch(FileNotFoundException e){
            System.out.println("Text file does not exist at this location");
        }
        catch(Exception e){
            System.out.println("Text file is malformed");
        }
    }
}
