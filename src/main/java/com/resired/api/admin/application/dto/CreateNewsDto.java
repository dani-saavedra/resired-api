package com.resired.api.admin.application.dto;

import java.io.InputStream;

public record CreateNewsDto(String title, String content, String category,
                            Attachment image, Attachment details
) {
    public record Attachment(String name, InputStream inputStream) {

    }
}
