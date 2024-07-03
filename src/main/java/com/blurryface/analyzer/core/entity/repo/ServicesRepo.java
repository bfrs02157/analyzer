package com.blurryface.analyzer.core.entity.repo;

import com.blurryface.analyzer.core.entity.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesRepo extends JpaRepository<Services, Long> {

    @Query("SELECT s FROM Services s WHERE s.serviceTag = :serviceTag")
    Optional<Services> findByServiceTag(String serviceTag);

    @Query("SELECT s FROM Services s WHERE s.serviceTag IN (:serviceTag)")
    List<Services> findByServiceTagIn(List<String> serviceTag);
}
