package com.mybank.service.data;

import com.mybank.entity.Comment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private RedisTemplate<String, Comment> redisTemplate;

    public CommentService(RedisTemplate<String, Comment> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveComment(String senderLogin, String commentMessage) {
        Comment comment = new Comment();
        comment.setMessage(commentMessage);
        comment.setSender(senderLogin);
        redisTemplate.opsForList().leftPush("comment", comment);
    }

    public List<Comment> getAllComments() {
        return redisTemplate.opsForList().range("comment", 0, -1);
    }
}
