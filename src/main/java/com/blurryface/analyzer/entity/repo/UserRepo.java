package com.blurryface.analyzer.entity.repo;

import com.blurryface.analyzer.entity.model.Organization;
import com.blurryface.analyzer.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
}
