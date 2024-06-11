package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.repository.AdminNewsPort;
import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;
import com.resired.api.admin.domain.vo.NeighConfig;
import com.resired.api.admin.infraestructure.sql.jpa.NeighborhoodAdmJpaRepository;
import com.resired.api.admin.infraestructure.sql.jpa.NewsJpaRepository;
import com.resired.api.admin.infraestructure.sql.jpa.NotificationCategoryJpaRepository;
import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.resident.infraestructure.sql.orm.NewsOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AdminNeighborhoodAdapterSQL implements NotificationCategoryPort, AdminNeighborhoodPort, AdminNewsPort {

    private final NotificationCategoryJpaRepository notificationCategoryRepository;
    private final NeighborhoodAdmJpaRepository neighborhoodRepository;
    private final NewsJpaRepository newsJpaRepository;

    @Override
    public void createNewNotificationCategory(Integer neighborhoodId, String name, LevelNotificationEnum levelNotification) {
        NotificationCategoryOrm notificationCategoryOrm = new NotificationCategoryOrm();
        notificationCategoryOrm.setName(name);
        notificationCategoryOrm.setActive(true);
        notificationCategoryOrm.setNotificationCategory(levelNotification);
        notificationCategoryOrm.setNeighborhoodId(neighborhoodId);
        notificationCategoryRepository.save(notificationCategoryOrm);
    }

    @Override
    public void configNeighborhood(NeighConfig neighConfig, int towers, int homes) {
        NeighborhoodOrm neighborhoodOrm = neighborhoodRepository.findById(neighConfig.id()).get();
        neighborhoodOrm.setId(neighConfig.id());
        neighborhoodOrm.setCategory(neighborhoodOrm.getCategory());
        neighborhoodOrm.setUpdateDate(LocalDate.now(ZoneOffset.UTC));
        neighborhoodOrm.setGroupingType(neighConfig.groupingType());
        neighborhoodOrm.setResidenceType(neighConfig.residenceType());
        neighborhoodOrm.setHomes(homes);
        neighborhoodOrm.setTowers(towers);
        neighborhoodRepository.save(neighborhoodOrm);
    }

    public void deactivateNotificationCategory(Integer neighborhoodId, String name) {
        notificationCategoryRepository.deactivateNotificationCategory(neighborhoodId, name);
    }

    @Override
    public Integer createNeighborHood(CreateNeighborhoodVo neighbor) {
        NeighborhoodOrm entity = new NeighborhoodOrm(neighbor.name(), neighbor.address(), neighbor.city(), neighbor.stratum(), neighbor
            .communityType(), neighbor.category(), neighbor.securityCompany());
        NeighborhoodOrm neighborhoodOrm = neighborhoodRepository.save(entity);
        return neighborhoodOrm.getId();
    }

    @Override
    public void createNews(CreateNewsDto createNews, Integer neighborhoodId) {
        NewsOrm newsOrm = new NewsOrm();
        newsOrm.setTitle(createNews.title());
        newsOrm.setContent(createNews.content());
        newsOrm.setCategory(createNews.category());
        newsOrm.setNeighborhoodId(neighborhoodId);
        newsOrm.setImage(createNews.image().orElse(null));
        newsOrm.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));

        newsJpaRepository.save(newsOrm);
    }

    @Override
    public Neighborhood findNeighborhoodById(Integer id) {
        Optional<NeighborhoodOrm> optNeigh = neighborhoodRepository.findById(id);
        return optNeigh.map(NeighborhoodOrm::convertToEntity).orElse(null);
    }
}
