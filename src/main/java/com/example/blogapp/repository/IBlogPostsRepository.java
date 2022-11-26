package com.example.blogapp.repository;

import com.example.blogapp.model.BlogPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogPostsRepository extends JpaRepository<BlogPosts, Long> {
    List<BlogPosts> findAllByBlogUsers_UserId(Long userId);

    List<BlogPosts> findAllByBlogUsers_UserName(String userName);
}

