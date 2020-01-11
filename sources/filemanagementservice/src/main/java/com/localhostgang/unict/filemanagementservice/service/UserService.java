package com.localhostgang.unict.filemanagementservice.service;

import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository repository;

    public User register(User user) {
        user.setRoles(Collections.singletonList("USER"));
        return repository.save(user);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<String> getRole(User user) {
        return user.getRoles();
    }
}
