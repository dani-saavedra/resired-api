package com.resired.api.resident.domain.repository;

import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.resident.domain.entity.News;
import com.resired.api.security.domain.vo.InfoBlocks;

import java.util.List;

public interface NeighborhoodPort {

    List<News> getNews(Integer id);

    Neighborhood findById(Integer id);

    InfoBlocks getBlocksByNeighborhood(Integer neighborhood);
}
