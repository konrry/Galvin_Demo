import net.galvin.dubbo.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DubboConsumer {


    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        context.start();
        System.out.println(" context.start() ");
        DemoService demoService = context.getBean("demoService", DemoService.class);
        System.out.println(" demoService ");
        demoService.sayHello("Qchu");
        System.out.println(" sayHello ");
        System.in.read();
    }


}
