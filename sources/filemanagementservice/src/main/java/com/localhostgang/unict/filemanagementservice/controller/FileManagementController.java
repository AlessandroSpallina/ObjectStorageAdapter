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
        return "pong";
    }

    /*
    @GetMapping("/")
    public String index(Authentication auth, Principal principal) {
        //UserDetails userDetails = (UserDetails) principal;
        return "il login funziona correttamente\n (sei loggato come:" + principal.getName() +", "+ auth.getAuthorities()+")";
    }
*/

    // get #1
    @GetMapping(path = "/{id}") // l'id della prima get è quello dei files che sono stati inseriti da un utente
    public ResponseEntity<String> getFileLink(Authentication auth, @RequestBody Integer id) {
        return ResponseEntity.status(fileService.getFileLink(auth.getName(), id)).build();
    }

    // get #2
    @GetMapping(path = "/")
    public ResponseEntity<Iterable<File>> getFiles(Authentication auth) {
        return ResponseEntity.status(200).body(fileService.isOwner(auth.getName()));
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

        fileService.storeFile(id, f);
        // MinIO store

        // Salva su db objectname e bucket; risponde col file creato
        return ResponseEntity.status(200).body(new File());
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
