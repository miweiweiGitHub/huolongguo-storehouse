package org.meteorite.com;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EX_052100260
 * @title: NewTest
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-28 9:11
 */
@RunWith(SpringRunner.class)
@Slf4j
public class NewTest {

    @Test
    public void createDeployment() {
     String name = "happy day";
        switch (name){

            case "happy new year":
                log.info("happy new year ");
                break;
            case "happy day":
                log.info("happy day ");

            case "happy ":
                log.info("happy ");

            default:
                log.info("default ");
                break;

        }
        log.info("部署流程 ");

    }


    @Test
    public void isEmpty() {
        List<User> aCBao = new ArrayList<>();
//        aCBao.add("test");
        if (!CollectionUtils.isEmpty(aCBao)) {
            log.info("! isEmpty ");
        }else {
            log.info("isEmpty else");
        }

        aCBao.stream().forEach(e->{
            System.out.println(e.getName().equals(""));
        });

    }

   class User{
        private String name;

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }
   }

}
