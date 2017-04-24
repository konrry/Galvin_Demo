package net.galvin.spring.aop;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;

/**
 * Created by Administrator on 2017/2/24.
 */
public class Demo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        BeanFactory beanFactory = classPathXmlApplicationContext.getBeanFactory();
        HelloService hello = (HelloService) beanFactory.getBean("helloService");
        hello.hello();
    }

}
