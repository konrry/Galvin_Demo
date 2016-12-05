package net.galvin.rpc.demo1;

import net.galvin.rpc.comm.HelloService;
import net.galvin.rpc.comm.HelloServiceImpl;

import java.io.*;

/**
 * Created by qchu on 16-12-2.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        HelloService helloService = new HelloServiceImpl();
        File file = new File("/home/qchu/lvmm/files/HelloService.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(helloService);
        oos.flush();
        oos.close();
        fos.close();
    }

}
