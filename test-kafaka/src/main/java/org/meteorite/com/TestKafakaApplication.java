package org.meteorite.com;

import com.alibaba.fastjson.JSON;
import org.meteorite.com.base.contants.Contants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 */
@SpringBootApplication
public class TestKafakaApplication implements CommandLineRunner {
    public static void main( String[] args ) {
        SpringApplication.run(TestKafakaApplication.class,args);
        System.out.println( "TestKafakaApplication start ..........." );
    }

    @Override
    public void run(String... strings) throws Exception {

        JSON.DEFFAULT_DATE_FORMAT= Contants.DateTimeFormat.DATE_TIME_PATTERN;
    }
}
