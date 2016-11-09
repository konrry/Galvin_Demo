package net.galvin.comm.jmx.demo1;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class HelloAgent {

    private MBeanServer mbs = null;
    HelloWorld helloWorld = null;


    public HelloAgent() {
        /**
         * This is Agent Layer
         * 【1】代理层 是 MBean Server, 一个MBean Server 是注册了MBean的Java对象。
         * 【2】代理层提供了4个代理服务: timer, mointoring,dynamic MBean loading, relationship service.
         */
        this.mbs = MBeanServerFactory.createMBeanServer("HelloAgent");

        /**
         * This is Distributed Layer
         */
        HtmlAdaptorServer adaptorServer = new HtmlAdaptorServer();

        helloWorld = new HelloWorld();
        ObjectName adapterName = null;
        ObjectName helloWoldName = null;

        try {
            helloWoldName = new ObjectName("HelloAgent:name=helloWorld!");
            mbs.registerMBean(helloWorld,helloWoldName);

            adapterName = new ObjectName("HelloAgent:name=htmlAdapter,prot=9092");
            mbs.registerMBean(adaptorServer,adapterName);

            adaptorServer.setPort(9092);
            adaptorServer.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("HelloAgent is running ...");
        HelloAgent helloAgent = new HelloAgent();

//        while (true){
//            Thread.sleep(10000);
//            helloAgent.helloWorld.printGreeting();
//        }

    }

}
