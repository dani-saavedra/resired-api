package com.resired.api.admin.domain.repository;

import java.io.IOException;
import java.io.InputStream;

public interface FilePort {
    String uploadFileToBucket(String bucket, String name, InputStream file) throws IOException;
}
