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
            mc.putObject(minio_default_bucket, objname.toString() + "_" + f.getOriginalFilename(), Miscellaneous.MultipartToJavaFile(f).toString());

            Optional<File> temp_file = fileRepository.findById(id);

            if(!temp_file.isPresent()) { // qui non dovrebbe mai entrarci, storeFile() andrebbe usato solo dopo aver storato i metadati!
                throw new Exception();
            }

            File toSave = temp_file.get();
            toSave.setObjectname(objname.toString() + "_" + f.getOriginalFilename());
            toSave.setBucket(minio_default_bucket);

            return fileRepository.save(toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File();
    }


    public boolean fileExists (Integer id) {
        Optional<File> file = fileRepository.findById(id);
        if(file.isPresent())
            return true;
        else
            return false;
    }

    public boolean isFileOwned (Integer id, String email) {
        Optional<File> file = fileRepository.findById(id);
        if(!file.isPresent()) return false;
        if(!file.get().getOwner().getEmail().equals(email)) return false;
        return true;
    }



    public Iterable<File> listFilesOwned (String email) {
        User user = userRepository.findByEmail(email);
        return fileRepository.getFilesByOwner(user);
    }

    public Iterable<File> listAllFiles() {
        return fileRepository.findAll();
    }

    public String getFileLink (Integer id) {
        try {
            MinioClient mc = new MinioClient("http://" + minio_host + ":" + minio_port, minio_id, minio_pass);
            Optional<File> toFind = fileRepository.findById(id);
            return mc.presignedGetObject(toFind.get().getBucket(), toFind.get().getObjectname());
        } catch (InvalidEndpointException | InvalidPortException | InvalidKeyException | NoSuchAlgorithmException | NoResponseException | InvalidResponseException | XmlPullParserException | InvalidBucketNameException | InvalidExpiresRangeException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            e.printStackTrace();
        }
        return "https://zoomquilt.org/";
    }

    public void deleteMetadataAndFile (Integer id) {
        try {
            MinioClient mc = new MinioClient("http://" + minio_host + ":" + minio_port, minio_id, minio_pass);
            Optional<File> toDelete = fileRepository.findById(id);
            String bucket_name = toDelete.get().getBucket();
            String obj_name = toDelete.get().getObjectname();
            mc.removeObject(bucket_name, obj_name);
            fileRepository.deleteById(id);
        } catch (InvalidEndpointException | InvalidPortException | InvalidKeyException | NoSuchAlgorithmException | NoResponseException | InvalidResponseException | XmlPullParserException | InvalidBucketNameException | InvalidArgumentException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
    // per Admin
    public Iterable<File> getAllFiles () {
        return fileRepository.findAll();
    } */
}
