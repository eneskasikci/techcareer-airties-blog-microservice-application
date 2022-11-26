package com.example.blogapp.service;

import com.example.blogapp.model.BlogUsers;
import com.example.blogapp.repository.IBlogUsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogUsersService {

    private final IBlogUsersRepository blogUsersRepository;

    public BlogUsersService(IBlogUsersRepository blogUsersRepository) {
        this.blogUsersRepository = blogUsersRepository;
    }

    public BlogUsers saveBlogUsers(BlogUsers blogUsers){
        return blogUsersRepository.save(blogUsers);
    }

    public List<BlogUsers> getAllBlogUsers(){
        return blogUsersRepository.findAll();
    }

    public BlogUsers getBlogUserById(Long userId){
        return blogUsersRepository.findById(userId).orElse(null);
    }

    // Wer are getting users details with his ID,
    // This can be used if we want to provide the username.
    public BlogUsers getBlogUserByName(String userName){
        return blogUsersRepository.findByUserName(userName);
    }

    // If the provided user is not exists from the request in the Blog Application's database
    // Create the user accordingly.
    public BlogUsers saveBlogUserFromRequest(Long request_blogUserId, String request_userName){
        return blogUsersRepository.save(new BlogUsers(request_blogUserId,request_userName));
    }
}
