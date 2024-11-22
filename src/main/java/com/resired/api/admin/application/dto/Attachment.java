package com.resired.api.admin.application.dto;

import java.io.InputStream;

public record Attachment(String name, InputStream inputStream) {

}
