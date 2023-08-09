package com.fprojects.articles_list.controllers;

import com.fprojects.articles_list.services.MinioService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/minio")
public class MinioController {

    private final MinioService minioService;
    private final MinioClient minioClient;

    @Autowired
    public MinioController(MinioService minioService, MinioClient minioClient) {
        this.minioService = minioService;
        this.minioClient = minioClient;
    }

    @PostMapping("/apload")
    public String uploadFileToMinIO(@RequestParam("file") MultipartFile file,
                                    @RequestParam("prefix") String prefix) {
        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            String fileName = file.getOriginalFilename();
            minioService.putObject(prefix + fileName, in);
            return "File uploaded";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Something wrong";
    }

    @GetMapping("/download")
    public String downloadFile(@RequestParam("fileName") String fileName) throws Exception {
        return minioService.getObject(fileName);
    }

    @GetMapping("/get_files_by_prefix")
    public String getFilesList(@RequestParam("prefix") String filePrefix) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String str = "";
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket("notice-app").prefix(filePrefix).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            String objName = item.objectName();
            str += objName + ", ";
        }
        return str;
    }

    @GetMapping("/get_files_names")
    public String getFilesNames() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String str = "";
        Iterable<Result<Item>> results =
                minioClient.listObjects(ListObjectsArgs
                        .builder()
                        .bucket("notice-app")
                        .build());
        for (Result<Item> result : results) {
            Item item = result.get();
            String objName = item.objectName();
            str += objName + ", ";
        }
        return str;
    }
}
