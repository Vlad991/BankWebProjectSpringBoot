package com.mybank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class ReceiveMessage {  //client-side to server
    @NotNull(message = "type is required")
    private MessageType type;
    private String receiver;
    private String message;
}
