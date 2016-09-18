package net.Galvin.thread.demo.mq.opoc;

/**
 * Created by qchu on 16-9-18.
 */
public class P {

    private String lock;

    public P(String lock) {
        this.lock = lock;
    }

    public void setValue(){
        synchronized (lock){
            try {
                if(!"".equals(ValObj.val)){
                    System.out.println(" P Waiting ... ");
                    lock.wait();
                }
                String val = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("P---Val: " + val);
                ValObj.val = val;
                lock.notify();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
