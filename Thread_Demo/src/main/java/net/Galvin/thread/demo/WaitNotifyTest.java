package net.Galvin.thread.demo;

/**
 * Created by qchu on 16-9-17.
 */
public class WaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();
        new MyThread1(lock).start();
        Thread.sleep(5000);
        new MyThread2(lock).start();

    }

}

class MyThread1 extends Thread {

    private Object lock;

    public MyThread1(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock){
                System.out.println(" start = " + System.currentTimeMillis());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.wait();
                System.out.println(" end = " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class MyThread2 extends Thread {

    private Object lock;

    public MyThread2(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println(" start = " + System.currentTimeMillis());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notify();
            System.out.println(" end = " + System.currentTimeMillis());
        }
    }
}

