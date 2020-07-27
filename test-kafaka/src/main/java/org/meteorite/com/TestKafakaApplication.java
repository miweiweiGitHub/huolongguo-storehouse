package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 */
@SpringBootApplication
public class TestKafakaApplication {
    public static void main( String[] args ) {
        SpringApplication.run(TestKafakaApplication.class,args);
        System.out.println( "TestKafakaApplication start ..........." );
    }
}
