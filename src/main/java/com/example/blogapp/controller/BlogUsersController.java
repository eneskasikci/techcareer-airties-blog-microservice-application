package com.example.blogapp.controller;

import com.example.blogapp.model.BlogUsers;
import com.example.blogapp.service.BlogPostService;
import com.example.blogapp.service.BlogUsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogApp/users")
public class BlogUsersController {
    private final BlogUsersService blogUsersService;

    public BlogUsersController(BlogUsersService blogUsersService) {
        this.blogUsersService = blogUsersService;
    }

    // http://localhost:7777/api/blogApp/users/createBlogUser
    @PostMapping("/createBlogUser")
    public BlogUsers createBlogUser(@RequestBody BlogUsers blogUsers){
        return blogUsersService.saveBlogUsers(blogUsers);
    }

    // http://localhost:7777/api/blogApp/users/getAllBlogAppUsers
    @GetMapping("/getAllBlogAppUsers")
    public List<BlogUsers> getAllUsers(){
        return blogUsersService.getAllBlogUsers();
    }
}
