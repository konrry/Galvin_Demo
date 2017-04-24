package net.galvin.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by Administrator on 2017/2/24.
 */
public class HelloInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("---------------------------------- before invoke ----------------------------------");
        invocation.proceed();
        System.out.println("---------------------------------- after invoke ----------------------------------");
        return null;
    }

}
