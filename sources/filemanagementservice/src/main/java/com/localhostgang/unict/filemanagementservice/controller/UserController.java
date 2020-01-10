package com.localhostgang.unict.filemanagementservice.controller;

import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class UserController {

    @Autowired
    UserRepository repository;

    @PostMapping("/register")
    public @ResponseBody User register(@RequestBody User user) {
        user.setRoles(Collections.singletonList("USER"));
        return repository.save(user);
    }


}
