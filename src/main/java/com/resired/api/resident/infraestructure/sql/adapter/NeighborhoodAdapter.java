package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.repository.NeighborhoodPort;
import com.resired.api.resident.infraestructure.sql.jpa.NeighborhoodJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.NewsOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NeighborhoodAdapter implements NeighborhoodPort {

    private NeighborhoodJpaRepository jpaRepository;

    @Override
    public List<NewsOrm> getNews(Long neighborhoodId) {
        return jpaRepository.getNewsByNeighborhood(neighborhoodId);
    }
}
