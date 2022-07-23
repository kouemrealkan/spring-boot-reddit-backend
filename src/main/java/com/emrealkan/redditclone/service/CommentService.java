package com.emrealkan.redditclone.service;

import com.emrealkan.redditclone.dto.CommentsDto;
import com.emrealkan.redditclone.exceptions.PostNotFoundException;
import com.emrealkan.redditclone.mapper.CommentMapper;
import com.emrealkan.redditclone.model.Comment;
import com.emrealkan.redditclone.model.NotificationEmail;
import com.emrealkan.redditclone.model.Post;
import com.emrealkan.redditclone.model.User;
import com.emrealkan.redditclone.repository.CommentRepository;
import com.emrealkan.redditclone.repository.PostRepository;
import com.emrealkan.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private static final String POST_URL = "";

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailBuilder mailBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto){
           Post post= postRepository.findById(commentsDto.getPostId())
                   .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));
         Comment comment =  commentMapper.map(commentsDto,post,authService.getCurrentUser());
         commentRepository.save(comment);

         String message = mailBuilder.build(post.getUser().getUsername() + "posted comment on your post" + POST_URL);
         sendCommentNotification(message,post.getUser());
    }

    private void sendCommentNotification(String message, User user){
        mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented on your post",user.getEmail(),message));
    }


    public List<CommentsDto> getAllCommentsForPost(Long postId) {
       Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId.toString()));
      return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(@PathVariable String userName){
        User user =  userRepository.findByUsername(userName)
                .orElseThrow(()->new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
