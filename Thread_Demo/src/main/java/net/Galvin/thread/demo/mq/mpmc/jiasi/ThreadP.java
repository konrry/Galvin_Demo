package net.Galvin.thread.demo.mq.mpmc.jiasi;


/**
 * Created by qchu on 16-9-18.
 */
public class ThreadP extends Thread {

    private P p;

    public ThreadP(P p) {
        this.p = p;
    }

    @Override
    public void run() {
        while (true){
            p.setValue();
        }
    }
}
