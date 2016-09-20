package net.Galvin.thread.demo;

/**
 * Created by qchu on 16-9-19.
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(" main start ... ");
        JoinThread joinThread = new JoinThread();
        joinThread.start();
        joinThread.join();
        System.out.println(" main end ... ");
    }

}

class JoinThread extends Thread{

    @Override
    public void run() {
        System.out.println(" This is JoinThread ... ");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

