package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class SpringRedis {
    public static void main( String[] args ) {
        SpringApplication.run(SpringRedis.class,args);
        System.out.println( "SpringRedis start ..........." );
    }
}
