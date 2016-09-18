package net.Galvin.thread.demo.mq.mpmc.jiasi;

/**
 * Created by qchu on 16-9-18.
 */
public class P {

    private String lock;

    public P(String lock) {
        this.lock = lock;
    }

    public void setValue(){
        try {
            synchronized (lock){
                System.out.println("P -- ThreadName: " + Thread.currentThread().getName());
                while(!"".equals(ValObj.val)){
                    System.out.println("P -- Wait -- ThreadName: " + Thread.currentThread().getName());
                    lock.wait();
                }
                String val = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("P -- Val: " + val);
                ValObj.val = val;
                lock.notifyAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
