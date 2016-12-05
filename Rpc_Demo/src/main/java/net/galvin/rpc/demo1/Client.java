package net.galvin.rpc.demo1;

import net.galvin.rpc.comm.HelloService;

import java.io.*;

/**
 * Created by qchu on 16-12-2.
 */
public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("/home/qchu/lvmm/files/HelloService.txt");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        HelloService helloService = (HelloService) ois.readObject();
        String result = helloService.hello("Qchu");
        System.out.println(result);
        ois.close();
        fis.close();
    }

}
