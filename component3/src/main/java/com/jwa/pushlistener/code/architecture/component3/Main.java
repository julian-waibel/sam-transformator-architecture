package com.jwa.pushlistener.code.architecture.component3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.messagemodel.FMessage;
import com.jwa.pushlistener.code.architecture.communication.ports.Ports;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws PortsException {
        final Ports ports = new Ports();

        // init ports
        ports.setPort("Port1",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Receiver")
                        .setParameter("PortParameters.Rmi.portRegistry", "11031")
                        .build()
        );
        ports.setReceiverHandler("Port1", msg -> {
            LOGGER.info("Port1 got called by other component");
            return Optional.absent();
        });
        ports.setPort("Port2",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Sender/SynchronousSender")
                        .setParameter("PortParameters.Rmi.hostname", "127.0.0.1")
                        .setParameter("PortParameters.Rmi.portRegistry", "11025")
                        .build()
        );
        ports.setPort("Port3",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Receiver")
                        .setParameter("PortParameters.Rmi.portRegistry", "11033")
                        .build()
        );
        ports.setReceiverHandler("Port3", msg -> {
            LOGGER.info("Port3 got called by other component");
            return Optional.absent();
        });
        ports.startReceiverPorts();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.connectSender("Port2");
        ports.executeSender("Port2", new FMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.stopPorts();
    }
}
