package org.babich.street.exception;

import java.util.StringJoiner;

/**
 * Response body when an error occurs on the server side
 * @author Vadim Babich
 */
public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .toString();
    }
}
