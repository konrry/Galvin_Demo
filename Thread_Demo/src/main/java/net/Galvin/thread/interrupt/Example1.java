package net.Galvin.thread.interrupt;

import javax.swing.plaf.TableHeaderUI;

/**
 * Created by Administrator on 2017/5/10.
 */
public class Example1 extends Thread {

    final static  Object lock = new Object();

    boolean stop=false;
    public static void main( String args[] ) throws Exception {
        Example1 thread = new Example1();
        System.out.println( "Starting thread..." );
        thread.start();
        Thread.sleep( 3000 );
        System.out.println("outer: "+thread.isInterrupted());
        thread.interrupt();//就是打一个标记而已
        System.out.println("outer: "+thread.isInterrupted());
        Thread.sleep( 3000 );
        System.out.println("outer: "+thread.isInterrupted());

    }
    public void run() {
        synchronized (lock){
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

