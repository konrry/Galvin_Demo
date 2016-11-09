package net.galvin.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.galvin.dubbo.service.DemoService;

/**
 * Created by Administrator on 2016/10/14.
 */
public class DemoServiceImpl implements DemoService {

    public void sayHello(String userName) {
        System.out.println(" Hello, " + userName);
    }

}
