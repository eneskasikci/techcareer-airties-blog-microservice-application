package com.example.blogapp.requests;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BlogUpdateRequest {
    @Id
    private Long request_blogUpdatedPostId;
    private String request_blogUpdatedPostTitle;
    private String request_blogUpdatedPostContent;
    private String request_blogUpdatedPost_userName;
}
