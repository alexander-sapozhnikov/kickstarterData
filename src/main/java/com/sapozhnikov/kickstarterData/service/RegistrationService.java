package com.sapozhnikov.kickstarterData.service;

import com.sapozhnikov.kickstarterData.entity.Role;
import com.sapozhnikov.kickstarterData.entity.User;
import com.sapozhnikov.kickstarterData.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    public String createUser(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return "User exists!";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return null;
    }
}
