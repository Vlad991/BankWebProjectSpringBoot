package com.mybank.service.data;

import com.mybank.entity.BlockedUser;
import com.mybank.entity.BlockingReason;
import com.mybank.entity.User;
import com.mybank.exception.UserAlreadyBlockedException;
import com.mybank.exception.UserNotBlockedException;
import com.mybank.exception.UserNotFoundException;
import com.mybank.repository.AddressRepository;
import com.mybank.repository.BlockedUserRepository;
import com.mybank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private BlockedUserRepository blockedUserRepository;
    private AddressRepository addressRepository;

    public UserService(UserRepository userRepository, BlockedUserRepository blockedUserRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.blockedUserRepository = blockedUserRepository;
        this.addressRepository = addressRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

//    @Transactional
//    public User saveUser(User user) {
//        addressRepository.save(user.getAddress());
//        return userRepository.save(user);
//    }

    @Transactional
    public User blockUser(String login, BlockingReason reason) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.getBlocked() != null) {
            throw new UserAlreadyBlockedException();
        }
        BlockedUser blockedUser = new BlockedUser();
        blockedUser.setBlockingReason(reason);
        blockedUser.setUser(user);
        user.setBlocked(blockedUser);
        return userRepository.save(user);  // todo save(ban)???
    }

    @Transactional
    public User unblockUser(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException();
        }
        BlockedUser blockedUser = user.getBlocked();
        if (blockedUser == null) {
            throw new UserNotBlockedException();
        }

        user.setBlocked(null);
        blockedUserRepository.delete(blockedUser);
        User checkUser = userRepository.findByLogin(user.getLogin());
        if (checkUser == null) {
            throw new UserNotFoundException();
        }
        return checkUser;
    }
}
