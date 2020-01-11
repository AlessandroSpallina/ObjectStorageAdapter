package com.localhostgang.unict.filemanagementservice.service;

import com.localhostgang.unict.filemanagementservice.entity.File;
import com.localhostgang.unict.filemanagementservice.entity.FileRepository;
import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    /*
    public Optional<File> getFileById(Integer id) {
        return fileRepository.findById(id);
    }

    public Iterable<File> getFileByEmail (String email) {
        return fileRepository.getFilesByOwner(userRepository.findByEmail(email));
    }
    */

    public File storeMetadata (File file, String email) { // auth.getname() restituisce proprio l'email
        User user = userRepository.findByEmail(email);
        file.setOwner(user);
        return fileRepository.save(file);
    }

    public boolean isFileOwned (Integer id, String email) {
        Optional<File> file = fileRepository.findById(id);
        if(!file.isPresent()) return false;
        if(!file.get().getOwner().getEmail().equals(email)) return false;
        return true;
    }

    /*
    // per Admin
    public Iterable<File> getAllFiles () {
        return fileRepository.findAll();
    } */
}
