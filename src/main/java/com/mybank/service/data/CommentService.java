package com.mybank.service.data;

import com.mybank.entity.Comment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private RedisTemplate<String, Comment> redisCommentsTemplate;

    public CommentService(RedisTemplate<String, Comment> redisCommentsTemplate) {
        this.redisCommentsTemplate = redisCommentsTemplate;
    }

    public void saveComment(String senderLogin, String commentMessage) {
        Comment comment = new Comment();
        comment.setMessage(commentMessage);
        comment.setSender(senderLogin);
        redisCommentsTemplate.opsForList().leftPush("comment", comment);
    }

    public List<Comment> getAllComments() {
        return redisCommentsTemplate.opsForList().range("comment", 0, -1);
    }
}
