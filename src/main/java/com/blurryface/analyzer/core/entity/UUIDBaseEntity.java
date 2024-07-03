package com.blurryface.analyzer.core.entity;

import com.blurryface.analyzer.core.entity.interfaces.Auditable;
import com.blurryface.analyzer.core.listener.AuditEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public class UUIDBaseEntity implements Serializable, Auditable {

    @Id
    @GeneratedValue
    @Column(name="id", columnDefinition = "BINARY(16)", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Embedded
    private AuditData auditData;

    @Column(name = "is_enabled")
    private Boolean isEnabled = false;
}
