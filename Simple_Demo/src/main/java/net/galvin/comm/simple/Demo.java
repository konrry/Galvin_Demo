package net.galvin.comm.simple;

/**
 * Created by qchu on 16-9-14.
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(" Hello World ... ");



        int status = 200;
        int status1 = 20;
        System.out.println(200 == status);
        System.out.println(new Integer(200) == status);
        System.out.println(new Integer(20) == status1);


        Integer integerA = 127;
        Integer integerB = 127;

        System.out.println(integerA == integerB);


    }

}
