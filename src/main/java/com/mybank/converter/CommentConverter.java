package com.mybank.converter;

import com.mybank.dto.MessageType;
import com.mybank.dto.SendMessage;
import com.mybank.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter {

    public List<SendMessage> toListCommentDTO(List<Comment> comments){
        return comments.stream()
                .map(entity-> fromCommentToDto(entity))
                .collect(Collectors.toList());
    }

    public SendMessage fromCommentToDto(Comment comment){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType(MessageType.COMMENT);
        sendMessage.setSender(comment.getSender());
        sendMessage.setMessage(comment.getMessage());
        return sendMessage;
    }
}
