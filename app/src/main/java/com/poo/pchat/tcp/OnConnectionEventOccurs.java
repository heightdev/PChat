package com.poo.pchat.tcp;

import com.poo.pchat.entities.Message;

/**
 * This interface represents an OnConnectionStatusChanged listener.
 * @author Felipe Porge Xavier - http://www.felipeporge.com
 */
public interface OnConnectionEventOccurs {

    /**
     * Called when tried to disconnect.
     * @param e - An error, if occurs.
     */
    void onDisconnect(ConnectionError e);

    /**
     * Called when tried to connect.
     * @param e - An error, if occurs.
     */
    void onConnect(ConnectionError e);

    /**
     * Called when received a message.
     * @param msg - Message received.
     */
    void onMessageReceived(Message msg);

    /**
     * Called when sent a message.
     * @param msg - Message sent.
     */
    void onMessageSent(Message msg);
}
