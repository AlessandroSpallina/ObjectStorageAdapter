package com.localhostgang.unict.filemanagementservice.controller;

import com.localhostgang.unict.filemanagementservice.entity.File;
import com.localhostgang.unict.filemanagementservice.service.FileService;
import com.localhostgang.unict.filemanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
public class FileManagementController {
    @Autowired
    private FileService fileService;

    @GetMapping("/ping")
    public String ping() {
        return "pongg";
    }

    // get #1
    @GetMapping(path = "/{id}") // l'id della prima get è quello dei files che sono stati inseriti da un utente
    public ResponseEntity<String> getFileLink(Authentication auth, @PathVariable Integer id) {

        if(!fileService.fileExists(id))
            return ResponseEntity.status(404).build();

        if(auth.getAuthorities().contains("ADMIN")) {
            return ResponseEntity.status(301).header("Location", fileService.getFileLink(id)).build();
            //return ResponseEntity.status(301).body(fileService.getFileLink(id));
        } else {
            if(fileService.isFileOwned(id, auth.getName())) {
                return ResponseEntity.status(301).header("Location", fileService.getFileLink(id)).build();
                //return ResponseEntity.status(301).body(fileService.getFileLink(id));
            } else {
                return ResponseEntity.status(404).build();
            }
        }
    }

    // get #2
    @GetMapping(path = "/")
    public ResponseEntity<Iterable<File>> getFiles(Authentication auth) {
        Iterable<File> toRet;

        if(auth.getAuthorities().contains("ADMIN")) {
            toRet = fileService.listAllFiles();
        } else {
            toRet = fileService.listFilesOwned(auth.getName());
        }
        return ResponseEntity.status(200).body(toRet);
    }

    // post #3
    @PostMapping(path="/", consumes="application/json", produces="application/json")
    public ResponseEntity<File> createMetadataFile(Authentication auth, @RequestBody File file) {
        File ret = fileService.storeMetadata(file, auth.getName());
        return ResponseEntity.status(200).body(ret);
    }

    // post #4
    @PostMapping(path = "/{id}", produces="application/json")
    public ResponseEntity<File> createFile(@RequestParam("file") MultipartFile f, Authentication auth, @PathVariable Integer id) {
        if(!fileService.isFileOwned(id, auth.getName())) {
            return ResponseEntity.status(400).build();
        }

        if(fileService.isWaitingFile(id)) {
            File ret = fileService.storeFile(id, f);
            return ResponseEntity.status(200).body(ret);
        }


        return ResponseEntity.status(400).build();

    }

    // delete #5
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteFile(Authentication auth, @PathVariable Integer id) {

        if(auth.getAuthorities().contains("ADMIN") || fileService.isFileOwned(id, auth.getName())) {
            fileService.deleteMetadataAndFile(id);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

}
