package net.galvin.rpc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by qchu on 16-12-4.
 */
public class IServiceImpl extends UnicastRemoteObject implements IService {

    private static final long serialVersionUID = 682805210518738166L;

    protected IServiceImpl() throws RemoteException {
    }

    public String queryName(String name) throws RemoteException {
        return "Hello, this is "+name;
    }

}
