package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@EnableSwagger2
@SpringBootApplication
public class TestMybatisApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(TestMybatisApplication.class,args);
        System.out.println( "TestMybatisApplication start ..........." );
    }
}
