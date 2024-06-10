package com.resired.api.admin.application.repository;

import com.resired.api.admin.application.dto.CreateNewsDto;

public interface AdminNewsPort {
    void createNews(CreateNewsDto createNewsDto, Integer neighborhoodId);
}
