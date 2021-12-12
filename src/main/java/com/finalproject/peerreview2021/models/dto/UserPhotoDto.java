package com.finalproject.peerreview2021.models.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class UserPhotoDto {
    private byte[] photo;

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }


}
