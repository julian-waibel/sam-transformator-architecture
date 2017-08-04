package com.jwa.pushlistener.code.architecture.component1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;
import com.jwa.pushlistener.code.architecture.messagemodel.AMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.CMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.DMessage;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        // TODO: implement me

        // example usage of CommunicationService
        try {
            final CommunicationService communicationService = new CommunicationService();
            final PortsService portsService = communicationService.getPortsService();

            portsService.setReceiverHandler(CommunicationService.Receivers.PORT2.name(),
                msg -> {
                    System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT2.name() + "' got called");
                    return Optional.absent();
                }
            );

            portsService.startReceiverPorts();

            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            portsService.connectSender(CommunicationService.Senders.PORT1.name());
            portsService.executeSender(CommunicationService.Senders.PORT1.name(), new AMessage());

            portsService.connectSender(CommunicationService.Senders.PORT3.name());
            portsService.executeSender(CommunicationService.Senders.PORT3.name(), new CMessage());

            portsService.connectSender(CommunicationService.Senders.PORT4.name());
            portsService.executeSender(CommunicationService.Senders.PORT4.name(), new DMessage());

            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            portsService.stopPorts();
        } catch (PortsServiceException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
