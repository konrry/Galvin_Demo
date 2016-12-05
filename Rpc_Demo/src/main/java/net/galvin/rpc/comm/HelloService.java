package net.galvin.rpc.comm;

import java.io.Serializable;

/**
 * Created by qchu on 16-12-2.
 */
public interface HelloService extends Serializable {


    String hello(String userName);


}
