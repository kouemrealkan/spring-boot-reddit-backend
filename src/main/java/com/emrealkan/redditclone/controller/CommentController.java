package com.emrealkan.redditclone.controller;

import com.emrealkan.redditclone.dto.CommentsDto;
import com.emrealkan.redditclone.mapper.CommentMapper;
import com.emrealkan.redditclone.model.User;
import com.emrealkan.redditclone.repository.CommentRepository;
import com.emrealkan.redditclone.repository.UserRepository;
import com.emrealkan.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId){

       return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));

    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName){
       return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(userName));
    }


}
