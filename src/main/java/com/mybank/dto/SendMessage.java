package com.mybank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class SendMessage { //Server to client-side
    @NotNull(message = "Type is required")
    private MessageType type;
    private String sender; // login?? todo
    private String message;
    private Set<String> activeUsers; //for Manager -> Clients, for Admin -> Managers
}
