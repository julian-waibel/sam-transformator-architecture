package com.jwa.pushlistener.ports.port;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.PortsException;

public interface Sender extends Port {
    void connect() throws PortsException;

    /**
     *
     * @param msg must not be null
     * @return Optional<MessageModel> if communication is synchronous, Optional.empty() if communication is asynchronous
     * @throws IllegalArgumentException if msg is null
     * @throws PortsException
     */
    Optional<MessageModel> execute(final MessageModel msg) throws IllegalArgumentException, PortsException;

    void disconnect();
}
