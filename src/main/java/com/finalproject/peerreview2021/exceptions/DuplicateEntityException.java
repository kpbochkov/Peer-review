package com.finalproject.peerreview2021.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 11:56
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
