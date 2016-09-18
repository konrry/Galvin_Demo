package net.galvin.jmx.demo;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by qchu on 16-9-18.
 */
public class MBeanServerTest {

    public static void main(String[] args) throws MalformedObjectNameException,
            IntrospectionException, InstanceNotFoundException,
            AttributeNotFoundException, ReflectionException, MBeanException,
            IOException {
        // System.gc();
        // System.out.println(JMXUtils.getYongGC());
        // System.out.println(JMXUtils.getFullGC());

        JMXUtils.traceAll(ManagementFactory.getPlatformMBeanServer());

    }

}
