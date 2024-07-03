package com.blurryface.analyzer.core.entity;

import com.blurryface.analyzer.core.entity.interfaces.Auditable;
import com.blurryface.analyzer.core.listener.AuditEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@Data
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public class LongBaseEntity implements Serializable, Auditable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Embedded
    private AuditData auditData;

    @Column(name = "is_enabled")
    private Boolean isEnabled = false;
}
