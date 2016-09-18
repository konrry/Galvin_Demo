package net.Galvin.thread.demo;

public class VolatileDemo {
    public static void main(String[] args) {
        final PrintString printString = new PrintString();
        new Thread(printString).start();
        printString.setContinuePrint(false);
    }
}

class PrintString implements Runnable {
    volatile private boolean isContinuePrint = true;
    public boolean isContinuePrint() {
        return isContinuePrint;
    }
    public void setContinuePrint(boolean continuePrint) {
        isContinuePrint = continuePrint;
    }

    public void methodA(){
        System.out.println(" methodA start ...");
        while (isContinuePrint){
            System.out.println(" Thread'name: " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(" methodA end ...");
    }

    public void run() {
        this.methodA();
    }
}
