package net.galvin.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/14.
 */
public class DubboProvider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        context.start();
        System.out.println(" SUCCESS ...  ");
        System.in.read();
    }

}
