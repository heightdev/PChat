package com.poo.pchat.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * This class represents a message.
 *
 */
public class Message implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    /* Types of message sent by the Clients:
     *      LOGIN - to log in the Server
     *      LOGOUT - to disconnect from the Server
     *      LIST - to receive the list of the users connected
     *      MESSAGE_FOR_ALL - to send a message for all users
     *      PRIVATE_MESSAGE - to send a private message
     *      RENAME_USERNAME - to change the username
     */
    public static final int LOGIN = 0, LOGOUT = 1, LIST = 2, MESSAGE_FOR_ALL = 3, PRIVATE_MESSAGE = 4, RENAME_USERNAME = 5;

    private int type;
    private String ipSender; // Número do IP de quem enviou a mensagem.
    private int portSender;
    private String fromUser;
    private String message;
    private String toUser;
    private SimpleDateFormat date;

    public Message(int type, String msg) {
        this.type = type;
        this.message = msg;

    }

    public Message(int type, String ipSender, int portSender, String fromUser, String message, String toUser,
                   SimpleDateFormat date) {
        this.type = type;
        this.ipSender = ipSender;
        this.portSender = portSender;
        this.fromUser = fromUser;
        this.message = message;
        this.toUser = toUser;
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIpSender() {
        return ipSender;
    }

    public void setIpSender(String ipSender) {
        this.ipSender = ipSender;
    }

    public int getPortSender() {
        return portSender;
    }

    public void setPortSender(int portSender) {
        this.portSender = portSender;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", ipSender='" + ipSender + '\'' +
                ", portSender=" + portSender +
                ", fromUser='" + fromUser + '\'' +
                ", message='" + message + '\'' +
                ", toUser='" + toUser + '\'' +
                '}';
    }
}
