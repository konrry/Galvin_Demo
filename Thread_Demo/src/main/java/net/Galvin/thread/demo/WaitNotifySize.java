package net.Galvin.thread.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qchu on 16-9-17.
 */
public class WaitNotifySize {

    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();

        new MyThreadA(lock).start();

        Thread.sleep(5000);

        new MyThreadB(lock).start();

    }

}

class MyList{

    private static List<String> list = new ArrayList<String>();

    public static void add(){
        list.add("Qchu");
    }

    public static int size(){
        return list.size();
    }

}


class MyThreadA extends Thread {

    private Object lock;

    public MyThreadA(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("MyThreadA.run start ...");
            try {
                if(MyList.size() != 5){
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyThreadA.run end ...");
        }
    }
}

class MyThreadB extends Thread {

    private Object lock;

    public MyThreadB(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("MyThreadB.run start ...");
            for (int i = 0; i < 10; i++) {
                MyList.add();
                if(MyList.size() == 5){
                    lock.notify();
                }
            }
            System.out.println("MyThreadB.run end ...");
        }
    }
}