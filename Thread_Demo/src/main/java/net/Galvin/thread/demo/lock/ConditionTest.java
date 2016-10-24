package net.Galvin.thread.demo.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qchu on 16-9-20.
 */
public class ConditionTest {

    public static void main(String[] args) {
        MyServ myServ = new MyServ();
        new MyThreadA(myServ).start();
        new MyThreadB(myServ).start();
        new MyThreadA(myServ).start();
        new MyThreadB(myServ).start();
    }

}


class MyServ {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private boolean hasValue = false;

    public void set(){
        try {
            lock.lock();
            while (hasValue){
                System.out.println(" ------ || ------ ");
                condition.await();
            }
            System.out.println(" ------ ");
            hasValue = true;
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void get(){
        try {
            lock.lock();
            while (!hasValue){
                System.out.println(" ###### || ###### ");
                condition.await();
            }
            System.out.println(" ###### ");
            hasValue = false;
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

class MyThreadA extends Thread{


    private MyServ serv;

    public MyThreadA(MyServ serv) {
        this.serv = serv;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            serv.set();
        }
    }
}

class MyThreadB extends Thread{


    private MyServ serv;

    public MyThreadB(MyServ serv) {
        this.serv = serv;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            serv.get();
        }
    }
}

