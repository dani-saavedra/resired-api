package com.resired.api.admin.application.dto;

import java.util.Optional;

public record CreateNewsDto(String title, String content, String category, Optional<String> image) {
}
