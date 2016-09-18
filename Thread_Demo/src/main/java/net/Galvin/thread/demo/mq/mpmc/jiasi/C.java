package net.Galvin.thread.demo.mq.mpmc.jiasi;

/**
 * Created by qchu on 16-9-18.
 */
public class C {

    private String lock;

    public C(String lock) {
        this.lock = lock;
    }

    public void getValue(){
        try {
            synchronized (lock){
                System.out.println("C -- ThreadName: " + Thread.currentThread().getName());
                while("".equals(ValObj.val)){
                    System.out.println("C -- Wait -- ThreadName: " + Thread.currentThread().getName());
                    lock.wait();
                }
                System.out.println("C -- Val: " + ValObj.val);
                ValObj.val = "";
                lock.notifyAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
