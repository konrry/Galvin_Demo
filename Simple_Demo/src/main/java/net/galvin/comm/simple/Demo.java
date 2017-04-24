package net.galvin.comm.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by qchu on 16-9-14.
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(" Hello World ... ");


        String[] strArr = ",222".split(",");
        for(String temp : strArr){
            System.out.println(temp);
        }


        Set<String> set = new HashSet<String>();
        set.add(null);

    }

}
