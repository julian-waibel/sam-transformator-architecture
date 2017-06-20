package com.jwa.pushlistener.communication.rmi.interfaces;

import com.jwa.pushlistener.communication.messagemodel.DMessage;
import com.jwa.pushlistener.communication.messagemodel.EMessage;
import com.jwa.pushlistener.communication.rmi.components.RMIRemoteInterface;

import java.rmi.RemoteException;

public interface RMIRemoteInterfaceNode3 extends RMIRemoteInterface {
    void execute(EMessage message) throws RemoteException;
    void execute(DMessage message) throws RemoteException;
}
