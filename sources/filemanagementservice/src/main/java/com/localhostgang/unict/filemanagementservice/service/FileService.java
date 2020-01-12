package com.localhostgang.unict.filemanagementservice.service;

import com.localhostgang.unict.filemanagementservice.entity.File;
import com.localhostgang.unict.filemanagementservice.entity.FileRepository;
import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${fms.minio_id}")
    private String minio_id;

    @Value("${fms.minio_pass}")
    private String minio_pass;

    @Value("${fms.minio_host}")
    private String minio_host;

    @Value("${fms.minio_port}")
    private String minio_port;

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

    public File storeFile (Integer id, MultipartFile f) {
        try {
            MinioClient mc = new MinioClient("http://" + minio_host + ":" + minio_port, minio_id, minio_pass);
            mc.putObject();
            // findme continua qui
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
        }
    }

    public boolean isFileOwned (Integer id, String email) {
        Optional<File> file = fileRepository.findById(id);
        if(!file.isPresent()) return false;
        if(!file.get().getOwner().getEmail().equals(email)) return false;
        return true;
    }

    public Iterable<File> isOwner (String email) {
        User user = userRepository.findByEmail(email);
        if(user.getRoles().contains("ADMIN")) {
            return fileRepository.findAll();
        }
        return fileRepository.getFilesByOwner(user);
    }

    public Integer getFileLink (String email, Integer id) {
        User user = userRepository.findByEmail(email);
        if(!fileRepository.findById(id).isPresent()){
            return 404;
        }
        if(!user.getId().equals(fileRepository.findById(id)) && user.getRoles().contains("USER")) {
            return 404;
        }
        return 301;
    }

    /*
    // per Admin
    public Iterable<File> getAllFiles () {
        return fileRepository.findAll();
    } */
}
