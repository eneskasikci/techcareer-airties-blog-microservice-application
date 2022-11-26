package com.example.blogapp.repository;

import com.example.blogapp.model.BlogUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogUsersRepository extends JpaRepository<BlogUsers, Long> {
    BlogUsers findByUserName(String userName);
}
