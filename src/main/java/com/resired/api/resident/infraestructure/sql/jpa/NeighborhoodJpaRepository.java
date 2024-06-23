package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.resident.infraestructure.sql.orm.NewsOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NeighborhoodJpaRepository extends JpaRepository<NeighborhoodOrm, Integer> {

    @Query(value = "SELECT ne FROM NewsOrm  ne WHERE ne.neighborhoodId = ?1 order by ne.createdDate DESC LIMIT 20")
    List<NewsOrm> getNewsByNeighborhood(Integer neighborhoodId);
}
