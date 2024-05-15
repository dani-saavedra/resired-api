package com.resired.api.resident.application.dto;

import com.resired.api.resident.domain.entity.News;

import java.util.List;

public record NewsResponse(List<News> news) {
}
