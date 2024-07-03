package com.blurryface.analyzer.core.entity.repo;

import com.blurryface.analyzer.core.entity.model.RSAKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RSAKeysRepo extends JpaRepository<RSAKeys, Long> {

    @Query("SELECT ke from RSAKeys ke")
    RSAKeys findKeyPair();

}
