package org.meteorite.com;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

    //服务访问次数，可以放在Redis中，实现分布式系统的访问计数
    Long counter = 0L;
    //使用LinkedList来记录滑动窗口的10个格子。
    LinkedList<Long> ll = new LinkedList<Long>();

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void testA() throws InterruptedException {
        AppTest counter = new AppTest();
        counter.doCheck();

    }


    private void doCheck() throws InterruptedException {
        while (true) {
            ll.addLast(counter);
            if (ll.size() > 10) {
                ll.removeFirst();
            }
            //比较最后一个和第一个，两者相差一秒
            if ((ll.peekLast() - ll.peekFirst()) > 100) {
                //To limit rate


            }
            Thread.sleep(100);
        }
    }
}
