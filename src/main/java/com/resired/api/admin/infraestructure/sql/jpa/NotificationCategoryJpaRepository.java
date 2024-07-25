package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationCategoryJpaRepository extends JpaRepository<NotificationCategoryOrm, Integer> {

    List<NotificationCategoryOrm> findAllByNeighborhoodId(Integer id);
}
