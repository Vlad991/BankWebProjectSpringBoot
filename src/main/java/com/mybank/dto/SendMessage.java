package com.mybank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class SendMessage { //Server to client-side
    private MessageType type;
    private String sender; // login?? todo
    private String message;
    private Set<String> activeClients;
}
