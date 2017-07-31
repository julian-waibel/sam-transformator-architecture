package com.jwa.pushlistener.code.architecture.component3;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.RmiPortFactory;
import com.jwa.pushlistener.code.architecture.communication.ports.Ports;

public final class CommunicationService {
    private static CommunicationService instance;
    private final Ports ports;

    public static CommunicationService getInstance() throws IllegalArgumentException {
        if (instance == null) {
            instance = new CommunicationService();
        }
        return instance;
    }

    public enum Receivers {
        PORT1,
        PORT3
    }

    public enum Senders {
        PORT2
    }

    public enum SynchronousSenders {
        PORT2
    }

    public enum AsynchronousSenders {}

    private CommunicationService() throws IllegalArgumentException {
        this.ports = new Ports(
                new PortFactoryConfigBuilder()
                        .setFactory("Rmi", new RmiPortFactory())
                        .build()
        );
        init();
    }

    private void init() throws IllegalArgumentException {
        ports.setPort(Receivers.PORT1.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("Rmi.portRegistry", "11031")
                        .build()
        );
        ports.setPort(SynchronousSenders.PORT2.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11025")
                        .build()
        );
        ports.setPort(Receivers.PORT3.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("Rmi.portRegistry", "11033")
                        .build()
        );
    }

    public final Ports getPorts() {
        return ports;
    }
}
