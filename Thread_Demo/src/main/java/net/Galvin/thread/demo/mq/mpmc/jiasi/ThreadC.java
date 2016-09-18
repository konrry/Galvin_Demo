package net.Galvin.thread.demo.mq.mpmc.jiasi;

/**
 * Created by qchu on 16-9-18.
 */
public class ThreadC extends Thread {

    private C c;

    public ThreadC(C c) {
        this.c = c;
    }

    @Override
    public void run() {
        while (true){
            c.getValue();
        }
    }
}
