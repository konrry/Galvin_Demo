package net.galvin.comm.jmx.demo1;

/**
 * Created by Administrator on 2016/9/25.
 */
public class HelloWorld implements HelloWorldMBean{

    private String greeting = null;

    public HelloWorld() {
        this.greeting = "Hello World! I am a Standard MBean";
    }

    public HelloWorld(String greeting) {
        this.greeting = greeting;
    }

    public void setGreeting(String greeting) {

        this.greeting = greeting;

    }

    public String getGreeting() {
        return this.greeting;
    }

    public void printGreeting() {
        System.out.println(greeting);
    }
}
