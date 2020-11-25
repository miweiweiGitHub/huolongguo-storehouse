package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class WXApp {
    public static void main( String[] args ) {
        SpringApplication.run(WXApp.class,args);
        System.out.println( "Hello World!" );
    }
}
