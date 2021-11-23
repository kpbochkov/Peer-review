package com.finalproject.peerreview2021.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 13:58
 */
public class UpdateEntityException extends RuntimeException{
    public UpdateEntityException(String type, String attribute) {
        super(String.format("Field %s of %s cannot be changed.", attribute, type));
    }
}
