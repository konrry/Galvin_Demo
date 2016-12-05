package net.galvin.rpc.demo2;

import net.galvin.rpc.comm.HelloService;
import net.galvin.rpc.comm.HelloServiceImpl;

import java.lang.reflect.Proxy;

/**
 * Created by qchu on 16-12-2.
 */
public class Client {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        Object object = Proxy.newProxyInstance(Client.class.getClassLoader(),
                new Class[]{HelloService.class},new ProxyHandler(helloService));
        HelloService proxy = (HelloService) object;
        String result = helloService.hello("Qchu");
        System.out.println("result: "+result);
    }

}
