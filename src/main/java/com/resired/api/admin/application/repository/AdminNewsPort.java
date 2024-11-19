package com.resired.api.admin.application.repository;

import com.resired.api.admin.application.dto.CreateNewsDto;

public interface AdminNewsPort {
    void createNews(CreateNewsDto newsRequest, Integer neighborhoodId, String imageUrl, String detail);
}
