package net.galvin.comm.simple;

/**
 * Created by Administrator on 2017/5/4.
 */
public class HelloService {



    public void hello(){

        System.out.println("before exception");
        int a = 1;
        int b = 0;
        int c = a/0;
        System.out.println("before exception");

    }


}
