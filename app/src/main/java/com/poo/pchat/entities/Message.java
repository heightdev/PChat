package com.poo.pchat.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * This class represents a message.
 *
 * @author Adjamilton Medeiros de Almeida Junior - http://github.com/ajunior
 * @author Felipe Porge Xavier - http://www.felipeporge.com
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
    private String ipSender; // IP number of who sent the message.
    private String fromUser;
    private String message;
    private String toUser;
    private Date date;
    private Object extra;

    /**
     * Constructor method.
     * @param type - Message type.
     * @param msg - Message.
     */
    public Message(int type, String msg) {
        this.type = type;
        this.message = msg;
    }

    /**
     * Constructor method.
     * @param type - Message type.
     * @param extra - Message extras.
     */
    public Message(int type, Object extra) {
        this.type = type;
        this.extra = extra;
    }

    /**
     * Constructor method.
     * @param type - Message type.
     * @param ipSender - Sender IP.
     * @param fromUser - From username.
     * @param msg - Message.
     */
    public Message(int type, String ipSender, String fromUser, String msg) {
        this.type = type;
        this.message = msg;
        this.ipSender = ipSender;
        this.fromUser = fromUser;
        this.date = Calendar.getInstance().getTime();
    }

    /**
     * Constructor method.
     * @param type - Message type.
     * @param ipSender - Sender IP.
     * @param fromUser - From username.
     * @param message - Message.
     * @param toUser - User to receive.
     */
    public Message(int type, String ipSender, String fromUser, String message, String toUser) {
        this.type = type;
        this.ipSender = ipSender;
        this.fromUser = fromUser;
        this.message = message;
        this.toUser = toUser;
        this.date = Calendar.getInstance().getTime();
    }

    /**
     * Gets message type.
     * @return - Message type.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets message type.
     * @param type - Message type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Gets sender IP.
     * @return - Sender IP.
     */
    public String getIpSender() {
        return ipSender;
    }

    /**
     * Sets sender IP.
     * @param ipSender - Sender IP.
     */
    public void setIpSender(String ipSender) {
        this.ipSender = ipSender;
    }

    /**
     * Gets message extras.
     * @return - Messages extras.
     */
    public Object getExtra() {
        return extra;
    }

    /**
     * Sets message extras.
     * @param extra - Message extras.
     */
    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * Gets 'from' username.
     * @return - 'From' username.
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * Sets 'from' username.
     * @param fromUser - 'From' username.
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * Gets message.
     * @return - Message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     * @param message - Message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets 'to' user.
     * @return - 'to' user.
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * Sets 'to' user.
     * @param toUser - 'to' user.
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    /**
     * Gets message date.
     * @return - Message date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets message date.
     * @param date - Message date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", ipSender='" + ipSender + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", message='" + message + '\'' +
                ", toUser='" + toUser + '\'' +
                '}';
    }
}
