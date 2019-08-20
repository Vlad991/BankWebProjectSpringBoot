package com.mybank.controller;

import com.mybank.dto.UserDTO;
import com.mybank.entity.BlockingReason;
import com.mybank.service.UserControllerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    private UserControllerService userControllerService;

    public AdminController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
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
