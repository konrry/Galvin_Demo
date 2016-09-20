package net.Galvin.thread.demo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qchu on 16-9-20.
 */
public class HoldCountTest {

    public static void main(String[] args) {

        Service service = new Service();
        service.testMethodA();

    }

}


class Service {

    private ReentrantLock lock = new ReentrantLock();

    public void testMethodA(){

        try {
            lock.lock();
            System.out.println(" testMethodA --- 1: " + lock.getHoldCount());
            testMethodB();
        }finally {
            lock.unlock();
        }
        System.out.println(" testMethodA --- 2: " + lock.getHoldCount());
    }

    public void testMethodB(){

        try {
            lock.lock();
            System.out.println(" testMethodB --- 1: " + lock.getHoldCount());
        }finally {
            lock.unlock();
        }
        System.out.println(" testMethodB --- 2: " + lock.getHoldCount());
    }

}

