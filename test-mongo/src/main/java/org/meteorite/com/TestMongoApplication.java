package org.meteorite.com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 */
@SpringBootApplication
public class TestMongoApplication {
    public static void main( String[] args ) {
        SpringApplication.run(TestMongoApplication.class,args);
        System.out.println( "TestMongoApplication start ..........." );
    }
}
