package com.localhostgang.unict.filemanagementservice.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Miscellaneous {

    // thx to https://reviewdb.io/posts/1504810616200/how-to-convert-multipartfile-to-java-io-file-in-spring
    public static File MultipartToJavaFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
