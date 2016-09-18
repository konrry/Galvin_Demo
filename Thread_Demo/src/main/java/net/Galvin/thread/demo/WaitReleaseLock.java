package net.Galvin.thread.demo;

public class WaitReleaseLock {

    public static void main(String[] args) {
        Object lock = new Object();
        new ThreadA(lock).start();
        new ThreadB(lock).start();
    }

}


class Service {
    public void testMethod(Object lock){
        try {
            synchronized (lock){
                System.out.println(" testMethod start ... ");
                lock.wait();
                System.out.println(" testMethod end ... ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class ThreadA extends Thread{
    private Object lock;

    public ThreadA(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.testMethod(lock);
    }
}

class ThreadB extends Thread{
    private Object lock;

    public ThreadB(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.testMethod(lock);
    }
}
