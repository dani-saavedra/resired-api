package com.resired.api.admin.application.dto;

public record CreateNewsDto(String title, String content, String category,
                            Attachment image, Attachment details
) {
}
