package com.poo.pchat.tcp;

/**
 * This interface represents an OnConnectionStatusChanged listener.
 * @author Felipe Porge Xavier - http://www.felipeporge.com
 */
public interface OnConnectionStatusChanged {

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
}
