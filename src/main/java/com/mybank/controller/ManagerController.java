package com.mybank.controller;

import com.mybank.dto.UserDTO;
import com.mybank.service.UserControllerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {
    private UserControllerService userControllerService;

    public ManagerController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @GetMapping(value = "/active_clients")
    public List<UserDTO> findActiveClients() {
        return userControllerService.findAll(); // todo only clients (in websocket)
    }

//    @GetMapping(value = "/connect")
//    public  clientCards(@PathVariable("login") String login) {
//    }
}
