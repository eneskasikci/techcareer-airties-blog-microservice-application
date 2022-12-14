package com.example.blogapp.controller;

import com.example.blogapp.model.BlogPosts;
import com.example.blogapp.requests.BlogCreateRequests;
import com.example.blogapp.requests.BlogDeleteRequest;
import com.example.blogapp.requests.BlogUpdateRequest;
import com.example.blogapp.responses.BlogResponse;
import com.example.blogapp.service.BlogPostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogApp/posts")
public class BlogPostsController {

    private final BlogPostService blogPostService;

    public BlogPostsController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }
    // http://localhost:7777/api/blogApp/posts/createBlogPost
    @PostMapping("/createBlogPost")
    public BlogPosts createBlogPost(@RequestBody BlogCreateRequests blogCreateRequests){
        return blogPostService.createBlogPosts(blogCreateRequests);
    }
    // To get the all the blog posts from the database
    // http://localhost:7777/api/blogApp/posts/getAllPosts
    // To get the all the blog posts from the database by any user
    // http://localhost:7777/api/blogApp/posts/getAllPosts?userId=X (X is the user id)

    @GetMapping("/getAllPosts")
    public List<BlogResponse> getAllDiaryPosts(@RequestParam Optional<Long> userId){
        return blogPostService.getAllBlogPosts(userId);
    }
    // To get the all the blog posts from the database
    // http://localhost:7777/api/blogApp/posts/getAllPostsFromUser/{userName}
    @GetMapping("/getAllPostsFromUser/{userName}")
    public List<BlogResponse> getPostsFromUser(@PathVariable String userName){
        return blogPostService.getAllBlogPostsFromUser(userName);
    }

    @PostMapping("/blogPostWithImage")
    public BlogPosts createBlogPostWithImage(@RequestParam("request_blogTitle") String blogTitle,
                                             @RequestParam("request_blogContent") String blogContent,
                                             @RequestParam("request_userName") String userName,
                                             @RequestParam("request_blogUserId") Long blogUserId,
                                             @RequestParam("image") MultipartFile blogImage) throws IOException {
        BlogCreateRequests blogCreateRequests = new BlogCreateRequests();
        blogCreateRequests.setRequest_blogTitle(blogTitle);
        blogCreateRequests.setRequest_blogContent(blogContent);
        blogCreateRequests.setRequest_userName(userName);
        blogCreateRequests.setRequest_blogUserId(blogUserId);
        return blogPostService.createBlogPostWithImage(blogCreateRequests, blogImage);
    }

    // After given its ID, it shows the Post
    // http://localhost:7777/api/blogApp/posts/1 -> this brings the first post in the DB if GetMapping
    @GetMapping("/{postId}")
    public BlogPosts getOnePost(@PathVariable Long postId){
        return blogPostService.getPostFromPostId(postId);
    }

    // http://localhost:7777/api/blogApp/posts/getImageFromPost/4
    @GetMapping("/getImageFromPost/{postId}")
    public ResponseEntity<byte[]> getImageFromPost(@PathVariable Long postId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return blogPostService.getImageFromPost(postId, headers);
    }

    // After given its ID, updates the Post
    // http://localhost:7777/api/blogApp/posts/updatePost/1 -> this updates the first post in the DB, select PutMapping
    @PutMapping("/updatePost/{postId}")
    public BlogPosts updatePostById(@PathVariable Long postId, @RequestBody BlogUpdateRequest updatePost){
        return blogPostService.updatePostById(postId, updatePost);
    }

    // This function is made for the Register Login Gateway
    // http://localhost:7777/api/blogApp/posts/updatePostIfUserIsOwner
    // To update the post from the gateway request, we check if the user is the owner of the post
    @PutMapping("/updatePostIfUserIsOwner")
    public ResponseEntity<?> updatePostIfUserIsOwner(@RequestBody BlogUpdateRequest updatePost){
        return blogPostService.updatePostIfUserIsOwner(updatePost);
    }

    // delete post if the user is the owner of the post
    // http://localhost:7777/api/blogApp/posts/deletePostIfUserIsOwner
    @DeleteMapping("/deletePostIfUserIsOwner")
    public ResponseEntity<?> deletePostIfUserIsOwner(@RequestBody BlogDeleteRequest postDeleteRequest){
        return blogPostService.deletePostIfUserIsOwner(postDeleteRequest);
    }
}
