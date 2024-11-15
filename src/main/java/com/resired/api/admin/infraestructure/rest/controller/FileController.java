package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.infraestructure.gcp.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final Image googleCloudStorageService;

    public FileController(Image googleCloudStorageService) {
        this.googleCloudStorageService = googleCloudStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = googleCloudStorageService.uploadImageWithDetails(file);
        return ResponseEntity.ok("File uploaded successfully. File URL: " + fileUrl);
    }

    @PostMapping("/download/{url}")
    public ResponseEntity<String> uploadFile(@PathVariable String url) throws IOException {
        String fileUrl = googleCloudStorageService.getImageWithSignedUrl(url);
        return ResponseEntity.ok("File uploaded successfully. File URL: " + fileUrl);
    }


}

