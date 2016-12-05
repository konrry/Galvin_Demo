package net.galvin.rpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by qchu on 16-12-4.
 */
public interface IService extends Remote {

    String queryName(String no) throws RemoteException;

}
