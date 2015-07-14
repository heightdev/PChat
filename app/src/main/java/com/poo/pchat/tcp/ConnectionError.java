package com.poo.pchat.tcp;

/**
 * This class represents a connection error.
 * @author Felipe Porge Xavier - http://www.felipeporge.com
 */
public class ConnectionError {

    private int code;
    private String cause;
    private String description;

    /**
     * Disabled constructor method.
     */
    private ConnectionError(){}

    /**
     * Constructor method.
     * @param code - Error code.
     * @param cause - Cause.
     */
    public ConnectionError(int code, String cause){
        this.code = code;
        this.cause = cause;
        this.description = "";
    }

    /**
     * Constructor method.
     * @param code - Error code.
     * @param cause - Cause.
     * @param description - Description.
     */
    public ConnectionError(int code, String cause, String description){
        this.code = code;
        this.cause = cause;
        this.description = description;
    }

    /**
     * Gets error code.
     * @return - Error code.
     */
    public int getCode(){
        return this.code;
    }

    /**
     * Gets error cause.
     * @return - Error cause.
     */
    public String getCause(){
        return this.cause;
    }

    /**
     * Gets error description.
     * @return - Error description.
     */
    public String getDescription(){
        return this.description;
    }
}
