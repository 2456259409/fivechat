package com.renjian.fivechase.serviceImpl;

import com.renjian.fivechase.Repository.UserRepository;
import com.renjian.fivechase.model.User;
import com.renjian.fivechase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getByNameAndPass(String username,String password) {
        return userRepository.getByUsernameAndPassword(username,password);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
