package net.Galvin.thread.demo.mq.mpmc.stack;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qchu on 16-9-18.
 */
public class MyStack {

    private List<String> stack = new LinkedList<String>();

    public void push(){
        synchronized (this){
            this.sop("push start");
            try {
                while(this.stack.size() >= 1){
                    this.sop("push wait ...");
                    this.wait();
                }
                String val = System.currentTimeMillis() + "_" + System.nanoTime();
                this.sop("push: " + val);
                this.stack.add(val);
                this.notifyAll();
                this.sop("push size: " + this.stack.size());
            }catch (Exception e){
                e.printStackTrace();
            }
            this.sop("push end");
        }
    }

    public void pop(){
        synchronized (this){
            this.sop("pop start");
            try {
                while(this.stack.size() < 1){
                    this.sop("pop wait");
                    this.wait();
                }
                String val = this.stack.remove(0);
                this.sop("pop: " + val);
                this.notifyAll();
                this.sop("pop size: " + this.stack.size());
            }catch (Exception e){
                e.printStackTrace();
            }
            this.sop("pop end");
        }
    }

    final private void sop(String log){
        System.out.println("ThreadName ===>> " + Thread.currentThread().getName() + " [ " + log + " ] ");
    }

}
