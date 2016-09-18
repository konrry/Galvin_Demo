package net.Galvin.thread.demo.mq.opoc;

import java.util.concurrent.Callable;

/**
 * Created by qchu on 16-9-18.
 */
public class Test {

    public static void main(String[] args) {
        String lock = new String("");
        P p = new P(lock);
        C c = new C(lock);
        new ThreadP(p).start();
        new ThreadC(c).start();
    }

}
