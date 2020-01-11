package com.localhostgang.unict.filemanagementservice.controller;

import com.localhostgang.unict.filemanagementservice.entity.File;
import com.localhostgang.unict.filemanagementservice.service.FileService;
import com.localhostgang.unict.filemanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
public class FileManagementController {
    @Autowired
    private FileService fileService;
    private UserService userService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    /*
    @GetMapping("/")
    public String index(Authentication auth, Principal principal) {
        //UserDetails userDetails = (UserDetails) principal;
        return "il login funziona correttamente\n (sei loggato come:" + principal.getName() +", "+ auth.getAuthorities()+")";
    }
*/

    @PostMapping(path="/", consumes="application/json", produces="application/json")
    public ResponseEntity<File> createMetadataFile(Authentication auth, @RequestBody File file) {
        File ret = fileService.storeMetadata(file, auth.getName());
        return ResponseEntity.status(200).body(ret);
    }

    /*
    @GetMapping("/")
    public Iterable<File> getFileById(Authentication auth, @PathVariable Integer id) throws IOException {
        // User user = userService.findByEmail(auth.getName());
        if(auth.getAuthorities().contains("ADMIN")) {
            return fileService.getAllFiles();
        }
        else {
            return null;
        }
    } */
}
