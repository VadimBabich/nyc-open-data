package org.babich.street.exception;

/**
 * When an error occurred in a third-party service
 * @author Vadim Babich
 */
public class ServiceExecutingException extends Exception {

    public ServiceExecutingException(String message) {
        super(message);
    }
}
