package net.galvin.rpc.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by qchu on 16-12-4.
 */
public class Server {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8088);
            // 创建一个服务
            IServiceImpl iService = new IServiceImpl();
            // 将服务绑定命名
            Naming.bind("rmi://127.0.0.1:8088/iService",iService);

            System.out.println("bind server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
