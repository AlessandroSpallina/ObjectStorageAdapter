package com.localhostgang.unict.filemanagementservice.service;

import com.localhostgang.unict.filemanagementservice.entity.*;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @Value("${fms.external_url}")
    private String external_url;

    public File storeMetadata (File file, String email) { // auth.getname() restituisce proprio l'email
        User user = userRepository.findByEmail(email);
        file.setOwner(user);

        return fileRepository.save(file);
    }

    public File storeFile (Integer id, MultipartFile f) {

        Optional<File> temp_file = fileRepository.findById(id);

        if(!temp_file.isPresent()) { // qui non dovrebbe mai entrarci, storeFile() andrebbe usato solo dopo aver storato i metadati!
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File toSave = temp_file.get();

        try {
            InetAddress[] nodes = InetAddress.getAllByName(minio_host);

            // viene calcolato un hash univoco da salvare in objectname per evitare collisioni sul bucket
            int objname = (toSave.getId().toString() + f.getOriginalFilename()).hashCode();

            for (InetAddress node : nodes) {

                // ---------------- routine di upload ----------------
                MinioClient mc = new MinioClient("http://" + node.getHostAddress() + ":" + minio_port, minio_id, minio_pass);
                //System.out.println(node.getHostAddress() + ":address - host :" + node.getHostName());

                if (!mc.bucketExists(minio_default_bucket)) {
                    mc.makeBucket(minio_default_bucket);
                }

                java.io.File tmp = Miscellaneous.multipartToJavaFileOnFS(f);
                mc.putObject(minio_default_bucket, objname + "_" + f.getOriginalFilename(), tmp.toString());
                tmp.delete();
                // --------------------------------------------------

            }

            toSave.setObjectname(objname + "_" + f.getOriginalFilename());
            toSave.setBucket(minio_default_bucket);
        } catch (UnknownHostException e) { // questa è eccezione del dns
            e.printStackTrace();
        } catch (InvalidPortException | InvalidEndpointException | InvalidKeyException | NoSuchAlgorithmException | NoResponseException | InvalidResponseException | XmlPullParserException | InvalidBucketNameException | InvalidArgumentException | RegionConflictException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) { // questa è eccezione di minio
            e.printStackTrace();
        }

        return toSave;
    }


    public boolean fileExists (Integer id) {
        Optional<File> file = fileRepository.findById(id);
        return file.isPresent();
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
            MinioClient mc = new MinioClient("http://" + external_url, minio_id, minio_pass);

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
