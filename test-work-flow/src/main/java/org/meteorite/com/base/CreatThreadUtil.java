package org.meteorite.com.base;

/**
 * @author EX_052100260
 * @title: CreatThreadUtil
 * @projectName huolongguo-storehouse
 * @description: 创建线程的四种方式
 * @date 2020-10-22 11:17
 */
public class CreatThreadUtil {

    public static void main(String[] args) {
//        MD5Util.encode(phone, CharEncoding.UTF_8);
        method1();
    }

    /**
     * 继承 thread 类
     */
    public static void method1() {

        Thread.currentThread().setName("继承thread类");
        System.out.println(Thread.currentThread().getName());
        ThreadDemo1 threadDemo1 = new ThreadDemo1();
        ThreadDemo1 threadDemo2 = new ThreadDemo1();
        threadDemo1.setName("test demo1");
        threadDemo2.setName("test demo2");

        threadDemo2.start();
        threadDemo1.start();


        MyRunnable myRunnable1 = new MyRunnable();
        MyRunnable myRunnable2 = new MyRunnable();
        myRunnable1.run();
        myRunnable2.run();
        Thread thread1 = new Thread(myRunnable1);
        Thread thread2 = new Thread(myRunnable2);
        thread1.setName("myRunnable1");
        thread2.setName("myRunnable2");
        thread1.start();
        thread2.start();

    }

    static class  ThreadDemo1 extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
