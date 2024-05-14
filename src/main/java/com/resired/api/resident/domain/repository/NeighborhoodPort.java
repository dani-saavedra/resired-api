package com.resired.api.resident.domain.repository;

import com.resired.api.resident.infraestructure.sql.orm.NewsOrm;

import java.util.List;

public interface NeighborhoodPort {

    List<NewsOrm> getNews(Long id);
}
