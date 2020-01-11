package com.localhostgang.unict.filemanagementservice.controller;

import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class FileManagementController {
    @Autowired
    private UserRepository repository;

    @GetMapping("ping")
    public @ResponseBody String ping() {
        return "pong";
    }

    @GetMapping("")
    public @ResponseBody String index() {
        return "il login funziona correttamente";
    }
}
