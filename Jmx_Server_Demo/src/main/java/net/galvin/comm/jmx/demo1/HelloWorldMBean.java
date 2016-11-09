package net.galvin.comm.jmx.demo1;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface HelloWorldMBean {

    void setGreeting(String greeting);

    String getGreeting();

    void printGreeting();

}
