package net.galvin.rpc.rmi;

import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by qchu on 16-12-4.
 */
public class Client {

    public static void main(String[] args) {
        try {
            // 根据命名获取服务
            IService server = (IService) Naming.lookup("rmi://127.0.0.1:8088/iService");
            // 调用远程方法
            String result = server.queryName("Qchu");
            // 输出调用结果
            System.out.println("result from remote : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
