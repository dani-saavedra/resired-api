package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.NewsOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsJpaRepository extends JpaRepository<NewsOrm, Integer> {
}
