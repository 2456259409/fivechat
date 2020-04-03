package com.renjian.fivechase.service;

import com.renjian.fivechase.model.User;

public interface UserService {
    User getByNameAndPass(String username,String password);

    User addUser(User user);

}
