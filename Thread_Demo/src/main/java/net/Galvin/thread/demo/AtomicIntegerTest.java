package net.Galvin.thread.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qchu on 16-9-17.
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {

        AddCountThread addCountThread = new AddCountThread();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
        new Thread(addCountThread).start();
    }

}


class AddCountThread extends Thread{

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 10000; i++) {
            System.out.println(atomicInteger.incrementAndGet());
        }
    }
}