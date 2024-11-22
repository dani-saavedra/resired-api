package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.resident.domain.entity.News;
import com.resired.api.resident.domain.repository.NeighborhoodPort;
import com.resired.api.resident.infraestructure.sql.jpa.NeighborhoodJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.BlockOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.vo.InfoBlocks;
import com.resired.api.utils.FormatDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class NeighborhoodAdapter implements NeighborhoodPort {

    private NeighborhoodJpaRepository jpaRepository;

    @Override
    public List<News> getNews(Integer neighborhoodId) {
        return jpaRepository.getNewsByNeighborhood(neighborhoodId).stream().map(orm ->
            new News(orm.getId(), orm.getTitle(), orm.getContent(), orm.getImage(), orm.getDetails(),
                orm.getCategory(), FormatDate.formatDate(orm.getCreatedDate()))).toList();
    }

    @Override
    public Neighborhood findById(Integer id) {
        Optional<NeighborhoodOrm> neighborhoodOrm = jpaRepository.findById(id);
        return neighborhoodOrm.map(orm ->
            new Neighborhood(orm.getId(), orm.getName(), orm.getAddress(), orm.getCity(), orm.getCategory(),
                orm.getSocioeconomicLevel())).orElse(null);

    }

    @Override
    public InfoBlocks getBlocksByNeighborhood(Integer neighborhood) {
        Optional<NeighborhoodOrm> orm = jpaRepository.findById(neighborhood);
        if (orm.isPresent()) {
            GroupingType groupingType = orm.get().getGroupingType();
            List<String> list = orm.get().getBlocks().stream().map(BlockOrm::getName).toList();
            return new InfoBlocks(groupingType, list);

        }
        return null;
    }
}
