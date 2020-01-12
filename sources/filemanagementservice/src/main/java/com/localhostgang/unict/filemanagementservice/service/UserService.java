package com.localhostgang.unict.filemanagementservice.service;

import com.localhostgang.unict.filemanagementservice.entity.User;
import com.localhostgang.unict.filemanagementservice.entity.UserRepository;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository repository;

    public User register(User user) {

        /*try {
            MinioClient mc = new MinioClient("http://" + minio_host + ":" + minio_port, minio_id, minio_pass);
            mc.makeBucket("fms-" + user.getNickname());
        } catch (InvalidEndpointException | InvalidPortException | InvalidBucketNameException | RegionConflictException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException | InvalidResponseException e) {
            e.printStackTrace();
        }*/


        user.setRoles(Collections.singletonList("USER"));
        return repository.save(user);
    }

    /*public User findByEmail(String email) {
        return repository.findByEmail(email);
    }*/

    /*public List<String> getRole(User user) {
        return user.getRoles();
    }*/
}
