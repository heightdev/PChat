package com.poo.pchat.tcp;

import android.os.AsyncTask;
import android.util.Log;
import com.poo.pchat.entities.Message;
import com.poo.pchat.util.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents an AsyncTask that controls the connection Socket.
 * @author Felipe Porge Xavier - http://www.felipeporge.com
 */
public class ClientAsyncTask extends AsyncTask<Void, Void, Void> {

    private Socket mSocket;
    private ObjectInputStream mObjIn;
    private ObjectOutputStream mObjOut;
    private String mServerIP;
    private int mPort;

    private String mUsername;
    private Queue<Message> mDataToSend = new LinkedList<Message>();
    private OnConnectionStatusChanged mListener;

    /**
     * Constructor method.
     * @throws IOException
     */
    public ClientAsyncTask(OnConnectionStatusChanged listener) throws IOException {
        mListener = listener;
        mUsername = Constants.DEFAULT_USERNAME + new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
    }

    /**
     * Verifies if socket is connected.
     * @return
     */
    public boolean isConnected(){
        return mSocket != null && !mSocket.isClosed();
    }

    /**
     * Start socket connection.
     * @param serverIP - Server ip.
     * @param port - Server port.
     */
    public void connect(String serverIP, int port){
        mServerIP = serverIP;
        mPort = port;
        execute();
    }

    /**
     * Close socket connection.
     */
    public void disconnect(){
        mDataToSend.add(new Message(Message.LOGOUT, ""));
    }

    /**
     * Gets the server address.
     * @return - Server IP and Port (format: 192.168.1.5:12000).
     */
    public String getServerAddress(){
        return mServerIP + ":" + mPort;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try{
            Log.w(Constants.DEBUG_TAG, mServerIP + ":" + mPort);
            mSocket = new Socket(mServerIP, mPort);
            Log.w(Constants.DEBUG_TAG, mUsername + ": Connected!");
        }catch(Exception e){
            e.printStackTrace();
            Log.w(Constants.DEBUG_TAG, mUsername + ": Connection failed!");
            mListener.onDisconnect(new ConnectionError(100, e.getCause().toString(), e.getMessage()));
            return null;
        }

        try {
            Message tmpMessage;
            mObjIn = new ObjectInputStream(mSocket.getInputStream());
            mObjOut = new ObjectOutputStream(mSocket.getOutputStream());

            // Provides feedback to main thread.
            mListener.onConnect(null);

            //The client needs to send a String containing the username on start.
            mObjOut.writeObject(mUsername);
            Log.w(Constants.DEBUG_TAG, mUsername + ": Logging in...");

            while(true) {
                if (mDataToSend.size() > 0) {
                    tmpMessage = mDataToSend.poll();
                    if (tmpMessage != null) {
                        try {
                            mObjOut.writeObject(tmpMessage);

                            if (tmpMessage.getType() == Message.LOGOUT) {
                                Log.w(Constants.DEBUG_TAG, mUsername + ": Logging out...");
                                break;
                            } else {
                                Log.w(Constants.DEBUG_TAG, mUsername + ": Sent message for all \"" + tmpMessage.getMessage() + "\".");
                            }
                        } catch (Exception e) {
                            mListener.onDisconnect(new ConnectionError(102, e.getCause().toString(), e.getMessage()));
                            break;
                        }
                    }
                }
            }
        }catch(Exception e){
            mListener.onConnect(new ConnectionError(101, e.getClass().getSimpleName(), e.getMessage()));
            return null;
        }

        try {
            if(mObjIn != null) mObjIn.close();
        } catch(Exception e) { } // not much else I can do

        try {
            if(mObjOut != null) mObjOut.close();
        } catch(Exception e) { } // not much else I can do

        try {
            if(mSocket != null) mSocket.close();
        } catch(Exception e) {
            mListener.onDisconnect(new ConnectionError(103, e.getCause().toString(), e.getMessage()));
            return null;
        } // not much else I can do

        mListener.onDisconnect(null);
        return null;
    }

    /**
     * This method can be used to send a message.
     * @param message - Message to send.
     */
    public void sendForAll(String message){
        mDataToSend.add(new Message(Message.MESSAGE_FOR_ALL, message));
    }
}
