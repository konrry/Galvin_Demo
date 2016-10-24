package net.Galvin.thread.demo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qchu on 16-9-19.
 */
public class ReentrantLockTest {


    public static void main(String[] args) {

        MyService myService = new MyService();
        new MyThread(myService).start();
        new MyThread(myService).start();
        new MyThread(myService).start();
        new MyThread(myService).start();
        new MyThread(myService).start();
        new MyThread(myService).start();
        new MyThread(myService).start();
        new MyThread(myService).start();


    }

}

class MyService {

    private Lock lock = new ReentrantLock();

    public void testMethod(){
        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadName: " + Thread.currentThread().getName() + ", i: " + (i+1));
        }
        lock.unlock();
    }

}

class MyThread extends Thread{

    private MyService myService;

    public MyThread(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        myService.testMethod();
    }
}

