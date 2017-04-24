package net.galvin.spring.aop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/2/24.
 */
public class HelloAdvisor extends StaticMethodMatcherPointcutAdvisor {

    public boolean matches(Method method, Class<?> targetClass) {
        return "hello".equals(method.getName());
    }

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            public boolean matches(Class<?> clazz) {
                return HelloService.class.isAssignableFrom(clazz);
            }
        };
    }
}
