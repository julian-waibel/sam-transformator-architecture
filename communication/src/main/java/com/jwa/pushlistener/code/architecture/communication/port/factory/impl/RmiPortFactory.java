package com.jwa.pushlistener.code.architecture.communication.port.factory.impl;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.factory.AbstractPortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;
import com.jwa.pushlistener.code.architecture.communication.port.impl.rmi.RmiReceiver;
import com.jwa.pushlistener.code.architecture.communication.port.impl.rmi.RmiSynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.port.impl.rmi.config.RmiReceiverConfig;
import com.jwa.pushlistener.code.architecture.communication.port.impl.rmi.config.RmiSenderConfig;

public final class RmiPortFactory implements AbstractPortFactory {
    private static final String PORTPARAMETER_PREFIX = "Rmi.";

    @Override
    public final Port createPort(final PortConfig config) throws IllegalArgumentException {
        final String portTypeParameterKey = "PortType";
        switch (config.getParameter(portTypeParameterKey)) {
            case "Receiver":
                return createReceiverPort(config);
            case "Sender/SynchronousSender":
                return createSynchronousSender(config);
            default:
                throw PortConfig.generateNotImplementedException(portTypeParameterKey);
        }
    }

    private RmiReceiver createReceiverPort(final PortConfig config) throws IllegalArgumentException {
        final int portRegistry = config.getPortParameterInt(PORTPARAMETER_PREFIX + "portRegistry");
        final RmiReceiverConfig receiverConfig = new RmiReceiverConfig(portRegistry);
        return new RmiReceiver(receiverConfig);
    }

    private RmiSynchronousSender createSynchronousSender(final PortConfig config) throws IllegalArgumentException {
        final String hostname = config.getPortParameter(PORTPARAMETER_PREFIX + "hostname");
        final int portRegistry = config.getPortParameterInt(PORTPARAMETER_PREFIX + "portRegistry");
        final RmiSenderConfig senderConfig = new RmiSenderConfig(hostname, portRegistry);
        return new RmiSynchronousSender(senderConfig);
    }
}
