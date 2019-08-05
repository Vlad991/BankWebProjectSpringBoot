package com.mybank.converter;

import com.mybank.dto.UserDTO;
import com.mybank.entity.BlockedUser;
import com.mybank.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setAddress(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setCardList(user.getCardList());
        if (user.getBlocked() != null) {
            userDTO.setBlocked(true);
        } else {
            userDTO.setBlocked(false);
        }
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());
        user.setSurname(userDTO.getSurname());
        user.setBirthday(userDTO.getBirthday());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setCardList(userDTO.getCardList());
//        if(userDTO.isBlocked()) {
//            BlockedUser blockedUser = new BlockedUser();
//            blockedUser.setUser(user);
//            user.setBlocked(blockedUser);
//        } todo
        return user;
    }

    public List<UserDTO> convertToListDto(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
