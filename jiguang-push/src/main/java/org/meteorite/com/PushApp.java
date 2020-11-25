package org.meteorite.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author EX_052100260
 * @title: PushApp
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-25 11:21
 */
//@AutoConfigureBefore({JacksonAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = "org.meteorite.com")
public class PushApp {

    public static void main( String[] args ) {

        SpringApplication.run(PushApp.class,args);

        System.out.println( "Hello World!" );
    }
}
