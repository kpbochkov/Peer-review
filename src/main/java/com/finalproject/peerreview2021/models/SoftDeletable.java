package com.finalproject.peerreview2021.models;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 10.12.2021
 * Time: 15:27
 */
public interface SoftDeletable {
    boolean isActive();

    void setActive(boolean active);
}
