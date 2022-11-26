package com.example.blogapp.requests;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BlogCreateRequests {
    @Id
    private Long request_blogId;
    private String request_blogTitle;
    private String request_blogContent;
    private String request_userName;
    private Long request_blogUserId;
}
