package com.holidu.interview.assignment.exception;

/**
 * When invalid arguments are passed to the REST endpoint.
 * @author Vadim Babich
 */
public class BadArgumentException extends Exception{

    public BadArgumentException(String message) {
        super(message);
    }
}
