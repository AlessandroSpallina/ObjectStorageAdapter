package com.localhostgang.unict.filemanagementservice.controller;

import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public @ResponseBody User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/provami")
    public @ResponseBody String provami() {
        return "esisto";
    }
/**/

}
