package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Project5Test {
    @Test
    void testError(){
        Project5 test = new Project5();
        test.error("Message");
    }

    @Test
    void testUsage(){
        Project5 test = new Project5();
        test.usage("Message");
    }

    @Test
    void testDoReadMe(){
        Project5 test = new Project5();
        boolean value = test.printResourceFile("readme.txt");
        assertThat(value, equalTo(true));
    }
}
