package com.blurryface.analyzer.core.entity.model;

import com.blurryface.analyzer.core.entity.LongBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "services")
public class Services extends LongBaseEntity {

    @Column(name = "tag")
    private String serviceTag;

    @Column(name = "secret")
    private String secret;

    @Column(name = "resource_hash")
    @Lob
    private String resourceHash;
}
