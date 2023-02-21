package edu.pdx.cs410J.tmestas;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.Collections;

/**
 * class to control the converting of a text file to a xml file
 */
public class Converter {
    /**
     * main method for the converter
     * @param args command line arguments
     */
    public static void main(String[] args){

        //usage: java -cp target/airline-2023.0.0.jar edu.pdx.cs410J.tmestas.Converter <Text File Path> <XML File Path>

        if(args.length > 2) {
            System.out.println("Too many arguments included, run -README for instructions");
            return;
        }

        if(args.length < 2){
            System.out.println("Not enough arguments included, run -README for instructions");
            return;
        }

        String textFilePath = args[0];
        String xmlFilePath = args[1];
        Airline newAirline;
        TextParser parser;

        try {
            FileReader f = new FileReader(textFilePath);
            BufferedReader b = new BufferedReader(f);
            parser = new TextParser(b);
        }catch(FileNotFoundException e){
            System.out.println("Text file does not exist at this location, exiting");
            return;
        }

        try{
            XmlDumper dumper = new XmlDumper(xmlFilePath);
            newAirline = parser.parse();
            Collections.sort(newAirline.getFlights());
            dumper.dump(newAirline);
        }catch (IOException e){
            System.out.println("Could not access directory for XML file, exiting");
        }catch (ParserException e){
            System.out.println("Text file is malformed");
        }
    }
}
