package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.entity.BlockingReason;
import com.mybank.service.CreditCardControllerService;
import com.mybank.service.UserControllerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    private UserControllerService userControllerService;

    public AdminController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @GetMapping(value = "/active_managers")
    public List<UserDTO> findActiveManagers() {
        return userControllerService.findAll(); // todo only managers (in websocket)
    }

    @GetMapping(value = "/active_clients")
    public List<UserDTO> findActiveClients() {
        return userControllerService.findAll(); // todo only clients (in websocket)
    }

    @PatchMapping(value = "/block/{login}")
    public UserDTO blockUser(@PathVariable("login") String login, @RequestBody BlockingReason reason) { //todo how???
        return userControllerService.blockUser(login, reason);
    }

    @DeleteMapping(value = "/block/{login}")
    public UserDTO unblockUser(@PathVariable("login") String login) {
        return userControllerService.unblockUser(login);
    }
}
