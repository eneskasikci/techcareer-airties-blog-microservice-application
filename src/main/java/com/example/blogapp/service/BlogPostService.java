package com.example.blogapp.service;

import com.example.blogapp.model.BlogPosts;
import com.example.blogapp.model.BlogUsers;
import com.example.blogapp.repository.IBlogPostsRepository;
import com.example.blogapp.requests.BlogCreateRequests;
import com.example.blogapp.requests.BlogDeleteRequest;
import com.example.blogapp.requests.BlogUpdateRequest;
import com.example.blogapp.responses.BlogResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostService {
    private final IBlogPostsRepository blogPostsRepository;
    private final BlogUsersService blogUsersService;

    public BlogPostService(IBlogPostsRepository blogPostsRepository, BlogUsersService blogUsersService) {
        this.blogPostsRepository = blogPostsRepository;
        this.blogUsersService = blogUsersService;
    }

    public BlogPosts createBlogPosts(BlogCreateRequests BlogPostRequest){
        BlogUsers BlogUsers = blogUsersService.getBlogUserById(BlogPostRequest.getRequest_blogUserId());
        if (BlogUsers == null){
            BlogUsers = blogUsersService.saveBlogUserFromRequest(BlogPostRequest.getRequest_blogUserId(), BlogPostRequest.getRequest_userName());
        }
        BlogPosts toSave = new BlogPosts();
        toSave.setBlogTitle(BlogPostRequest.getRequest_blogTitle());
        toSave.setBlogContent(BlogPostRequest.getRequest_blogContent());
        toSave.setBlogUsers(BlogUsers);
        return blogPostsRepository.save(toSave);
    }

    public List<BlogResponse> getAllBlogPosts(Optional<Long> userId){
        List<BlogPosts> list;
        if(userId.isPresent()){
            list = blogPostsRepository.findAllByBlogUsers_UserId(userId.get());
        }else {
            list = blogPostsRepository.findAll();
        }
        return list.stream().map(BlogResponse::new).collect(Collectors.toList());
    }

    public BlogPosts getPostFromPostId(Long postId){
        return blogPostsRepository.findById(postId).orElse(null);
    }

    public BlogPosts updatePostById(Long postId, BlogUpdateRequest updateBlog){
        Optional<BlogPosts> post = blogPostsRepository.findById(postId);
        if(post.isPresent()){
            BlogPosts toUpdate = post.get();
            toUpdate.setBlogTitle(updateBlog.getRequest_blogUpdatedPostTitle());
            toUpdate.setBlogContent(updateBlog.getRequest_blogUpdatedPostContent());
            blogPostsRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    // BUG: Returns Retrofit null and this creates a 500 error code in the gateway even though deletion is successfully made.
    // TODO: Fix this bug.
    public ResponseEntity<?> deletePostIfUserIsOwner(BlogDeleteRequest blogDeleteRequest){
        Optional<BlogPosts> post = blogPostsRepository.findById(blogDeleteRequest.getDeletionrequest_blogId());
        if (post.isPresent()){
            if (post.get().getBlogUsers().getUserName().equals(blogDeleteRequest.getDeletionrequest_userName())){
                blogPostsRepository.deleteById(blogDeleteRequest.getDeletionrequest_blogId());
                return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Post not deleted. You are not the owner of the post.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("No post found with the deletion request post ID.", HttpStatus.BAD_REQUEST);
    }

    // BUG: Returns Retrofit null and this creates a 500 error code in the gateway even though deletion is successfully made.
    // TODO: Fix this bug.
    public ResponseEntity<?> updatePostIfUserIsOwner(BlogUpdateRequest updatePost) {
        // check if there is a post with the requested id
        Optional<BlogPosts> post = blogPostsRepository.findById(updatePost.getRequest_blogUpdatedPostId());
        if (post.isPresent()){
            // check if the user is the owner of the post
            if (post.get().getBlogUsers().getUserName().equals(updatePost.getRequest_blogUpdatedPost_userName())){
                updatePostById(updatePost.getRequest_blogUpdatedPostId(), updatePost);
                return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("You are not the owner of the post", HttpStatus.BAD_REQUEST);
    }
    public List<BlogResponse> getAllBlogPostsFromUser(String userName){
        List<BlogPosts> list;
        if (userName != null){
            list = blogPostsRepository.findAllByBlogUsers_UserName(userName);
        } else{
            return null;
        }
        return list.stream().map(BlogResponse::new).collect(Collectors.toList());
    }
}
