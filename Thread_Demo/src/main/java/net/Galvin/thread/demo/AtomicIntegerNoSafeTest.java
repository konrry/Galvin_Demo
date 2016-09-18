package net.Galvin.thread.demo;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by qchu on 16-9-17.
 */
public class AtomicIntegerNoSafeTest {

    public static void main(String[] args) throws InterruptedException {

        MyService myService = new MyService();
        MyThread[] myThreadArr = new MyThread[5];
        for (int i = 0; i < 5; i++) {
            myThreadArr[i] = new MyThread(myService);
        }
        for (int i = 0; i < 5; i++) {
            myThreadArr[i].start();
        }
        Thread.sleep(5000);
        System.out.println(MyService.atomicLong.get());
    }

}

class MyService {

    public static AtomicLong atomicLong = new AtomicLong();

    public void addNum(){
        System.out.println(atomicLong.addAndGet(100));
        System.out.println(atomicLong.addAndGet(1));
    }

}

class MyThread extends Thread {

    MyService myService;

     public MyThread(MyService myService){
         this.myService = myService;
     }

    @Override
    public void run() {
        myService.addNum();
    }

}



