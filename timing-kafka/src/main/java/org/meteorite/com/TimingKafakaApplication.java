package org.meteorite.com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 */
@SpringBootApplication
public class TimingKafakaApplication implements CommandLineRunner {
    public static void main( String[] args ) {
        SpringApplication.run(TimingKafakaApplication.class,args);
        System.out.println( "TestKafakaApplication start ..........." );
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
