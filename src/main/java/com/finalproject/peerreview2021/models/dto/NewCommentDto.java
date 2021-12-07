package com.finalproject.peerreview2021.models.dto;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 06.12.2021
 * Time: 13:19
 */
public class NewCommentDto {
    @NotNull(message = "Comment can't be empty")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
