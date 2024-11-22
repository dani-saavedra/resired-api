package com.resired.api.admin.infraestructure.gcp;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.resired.api.admin.domain.repository.FilePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FileBucket implements FilePort {

    @Override
    public String uploadFileToBucket(String bucket, String name, InputStream file) throws IOException {
        Storage storage = StorageOptions.newBuilder().build().getService();
        BlobId blobId = BlobId.of(bucket, name);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.createFrom(blobInfo, file);
        return String.format("https://storage.googleapis.com/%s/%s", bucket, name);
    }
}
