package net.Galvin.thread.demo.mq.mpmc.stack;

/**
 * Created by qchu on 16-9-18.
 */
public class ThreadC extends Thread {

    private MyStack myStack;

    public ThreadC(MyStack myStack) {
        this.myStack = myStack;
    }

    @Override
    public void run() {
        while (true){
            myStack.pop();
        }
    }
}
