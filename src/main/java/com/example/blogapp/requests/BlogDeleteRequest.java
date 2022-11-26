package com.example.blogapp.requests;

import lombok.Data;

@Data
public class BlogDeleteRequest {
    private String deletionrequest_userName;
    private Long deletionrequest_blogId;
}
