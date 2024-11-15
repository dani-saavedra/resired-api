package com.resired.api.admin.infraestructure.gcp;

import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class Image {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final String BUCKET_NAME = "cover_image_resired";

    public Image() {

    }

    public String uploadImageWithDetails(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        log.info("Upload image with Details : {}", originalFilename);
        Storage storage = StorageOptions.newBuilder().build().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, originalFilename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.createFrom(blobInfo, file.getInputStream());
        String fileUrl = String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, originalFilename);
        log.info("Image uploaded successfully");
        return fileUrl;
    }

    public String getSignedUrl(String blobName) {
        BlobId blobId = BlobId.of(BUCKET_NAME, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        URL signedUrl = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES);
        return signedUrl.toString();
    }

    public String getImageWithSignedUrl(String url) {
        return getSignedUrl(url);
    }
}

