package com.localhostgang.unict.filemanagementservice.service;

import com.localhostgang.unict.filemanagementservice.entity.File;
import com.localhostgang.unict.filemanagementservice.entity.FileRepository;
import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import com.localhostgang.unict.filemanagementservice.util.Miscellaneous;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @Value("${fms.minio_default_bucket}")
    private String minio_default_bucket;

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

            // viene calcolato un hash univoco da salvare in objectname per evitare collisioni sul bucket
            Integer objname = (id.toString() + f.getOriginalFilename()).hashCode();
            mc.putObject(minio_default_bucket, objname.toString(), Miscellaneous.MultipartToJavaFile(f).toString());

            Optional<File> temp_file = fileRepository.findById(id);

            if(!temp_file.isPresent()) { // qui non dovrebbe mai entrarci, storeFile() andrebbe usato solo dopo aver storato i metadati!
                throw new Exception();
            }

            File toSave = temp_file.get();
            toSave.setObjectname(objname.toString());
            toSave.setBucket(minio_default_bucket);

            return fileRepository.save(toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File();
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
