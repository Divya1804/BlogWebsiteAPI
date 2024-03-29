package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServices postServices;

    @PostMapping("/user/{userId}/category/{catId}/post")
    private ResponseEntity<?> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("catId") Integer catId){
        PostDto postDto1 = postServices.createPost(postDto, userId, catId);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @PutMapping("/post/{postId}")
    private ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId){
        PostDto postDto1 = postServices.updatePost(postDto, postId);
        return new ResponseEntity<>(postDto1, HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts")
    private ResponseEntity<?> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy, @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir){
        PostResponse posts = postServices.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    private ResponseEntity<?> getPostById(@PathVariable("postId") Integer postId){
        PostDto postDto = postServices.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/post")
    private ResponseEntity<?> getPostByUser(@PathVariable("userId") Integer userId){
        List<PostDto> postDtos = postServices.getPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{catId}/post")
    private ResponseEntity<?> getPostByCategory(@PathVariable("catId") Integer catId){
        List<PostDto> postDtos = postServices.getPostByCategory(catId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    private ResponseEntity<?> deletePost(@PathVariable("postId") Integer postId){
        postServices.deletePost(postId);
        ApiResponse api = new ApiResponse("Post Deleted successfully" , true);
        return new ResponseEntity<>(api, HttpStatus.GONE);
    }

    @GetMapping("/post/search/{keyword}")
    private ResponseEntity<?> searchPost(@PathVariable("keyword") String keyword){
        List<PostDto> dtos = postServices.searchPost(keyword);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
