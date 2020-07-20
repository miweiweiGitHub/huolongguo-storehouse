package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class SpringOauthApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(SpringOauthApplication.class,args);
        System.out.println( "SpringOauthApplication start ..........." );
    }
}
