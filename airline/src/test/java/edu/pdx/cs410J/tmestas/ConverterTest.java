package edu.pdx.cs410J.tmestas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConverterTest {
    void testConverter(){
        Converter test = new Converter();
        String [] args = {"-pretty", "-"};
        //assertThat(test.checkPrettyPath(args), equalTo(false));
    }
}
