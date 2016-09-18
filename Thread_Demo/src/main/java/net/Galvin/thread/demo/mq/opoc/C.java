package net.Galvin.thread.demo.mq.opoc;

/**
 * Created by qchu on 16-9-18.
 */
public class C {

    private String lock;

    public C(String lock) {
        this.lock = lock;
    }

    public void getValue(){
        synchronized (lock){
            try {
                if("".equals(ValObj.val)){
                    System.out.println(" C Waiting ... ");
                    lock.wait();
                }
                System.out.println("C--Val: " + ValObj.val);
                ValObj.val = "";
                lock.notify();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
