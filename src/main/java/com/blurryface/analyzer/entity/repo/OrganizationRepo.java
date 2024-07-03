package com.blurryface.analyzer.entity.repo;

import com.blurryface.analyzer.entity.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepo  extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrgId(Long orgId);
}
