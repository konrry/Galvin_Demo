package net.galvin.rpc.demo2;

import net.galvin.rpc.comm.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by qchu on 16-12-2.
 */
public class ProxyHandler implements InvocationHandler{

    private Object proxy;

    public ProxyHandler(Object proxy){
        this.proxy = proxy;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxy,args);
    }




}
