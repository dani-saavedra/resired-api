package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.entity.News;

import java.util.List;

public interface NeighborhoodPort {

    List<News> getNews(Long id);
}
