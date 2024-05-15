package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.entity.News;
import com.resired.api.resident.domain.repository.NeighborhoodPort;
import com.resired.api.resident.infraestructure.sql.jpa.NeighborhoodJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NeighborhoodAdapter implements NeighborhoodPort {

    private NeighborhoodJpaRepository jpaRepository;

    @Override
    public List<News> getNews(Long neighborhoodId) {
        return jpaRepository.getNewsByNeighborhood(neighborhoodId).stream().map(orm ->
            new News(orm.getId(), orm.getTitle(), orm.getContent(), orm.getImage(),
                orm.getCategory(), orm.getCreatedDate())).toList();
    }
}
