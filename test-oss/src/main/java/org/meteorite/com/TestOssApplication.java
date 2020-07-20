package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class TestOssApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(TestOssApplication.class,args);
        System.out.println( "TestOssApplication start ..........." );
    }
}
