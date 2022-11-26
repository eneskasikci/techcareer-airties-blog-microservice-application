package com.example.blogapp.responses;

import com.example.blogapp.model.BlogPosts;
import lombok.Data;

@Data
public class BlogResponse {
    private Long response_blogId;
    private Long response_blogUserId;
    private String response_userName;
    private String response_blogTitle;
    private String response_blogContent;

    public BlogResponse(BlogPosts entity){
        this.response_blogId = entity.getBlogId();
        this.response_blogUserId = entity.getBlogUsers().getUserId();
        this.response_userName = entity.getBlogUsers().getUserName();
        this.response_blogTitle = entity.getBlogTitle();
        this.response_blogContent = entity.getBlogContent();
    }

}
