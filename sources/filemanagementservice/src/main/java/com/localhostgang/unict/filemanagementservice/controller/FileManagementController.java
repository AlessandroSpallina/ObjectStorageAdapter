package com.localhostgang.unict.filemanagementservice.controller;

import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileManagementController {
    @Autowired
    private UserRepository repository;

    /*@GetMapping("/")
    public String getFiles() {
        return "ciao";
    }*/

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }


}
