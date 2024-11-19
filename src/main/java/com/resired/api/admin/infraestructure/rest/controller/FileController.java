package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.infraestructure.gcp.FileBucket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileBucket googleCloudStorageService;

    public FileController(FileBucket googleCloudStorageService) {
        this.googleCloudStorageService = googleCloudStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = googleCloudStorageService.uploadFileToBucket("cover_image_resired",
            file.getOriginalFilename(), file.getInputStream());
        return ResponseEntity.ok("File uploaded successfully. File URL: " + fileUrl);
    }
}

