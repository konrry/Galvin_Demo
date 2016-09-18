package net.Galvin.thread.demo.mq.mpmc.stack;

/**
 * Created by qchu on 16-9-18.
 */
public class Test {

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        new ThreadP(myStack).start();
        new ThreadP(myStack).start();

        new ThreadC(myStack).start();
        new ThreadC(myStack).start();
    }

}
