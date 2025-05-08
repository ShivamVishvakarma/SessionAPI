package com.shivam.session.service;

import com.shivam.session.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService   {
    User createUser(User user);

    User loginUser(String username, String password);

    User getUserById(Long id);


}
