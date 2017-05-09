package net.galvin.comm.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by qchu on 16-9-14.
 */
public class Demo {

    public static void main(String[] args) {

        HelloService helloService = new HelloService();
        try {
            helloService.hello();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
