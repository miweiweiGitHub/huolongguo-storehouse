package org.meteorite.com;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;
import org.meteorite.com.base.em.SensitiveTypeEnum;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    public int flag = 1;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );

        ThreadFactory fixedFactory = new ThreadFactoryBuilder().setNameFormat("fixed-%d").build();
        CompletableFuture.runAsync(() -> {
            doSomething("TEST");
        }, Executors.newFixedThreadPool(1, fixedFactory));

    }

    private void doSomething(String test) {
        long start = System.currentTimeMillis();
        while (flag<100){
            System.out.println(test+flag/10);
            flag++;
        }
        long end = System.currentTimeMillis();
        System.out.println("shouldAnswerWithTrue：" + (end - start) + "\n");
    }

    @Test
    public void enumTrue()
    {

        for (SensitiveTypeEnum value : SensitiveTypeEnum.values()) {
//            System.out.println( value.ordinal());
        }

        System.out.println("yyyy-MM-dd HH:mm:ss".length());


    }

}
