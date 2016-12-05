package net.galvin.rpc.comm;

/**
 * Created by qchu on 16-12-2.
 */
public class HelloServiceImpl implements HelloService {

    private String defaultName = " Galvin";

    public String hello(String userName) {
        if(userName == null || userName.trim().equals("")){
            userName = defaultName;
        }
        return "Hello, "+userName;
    }
}
