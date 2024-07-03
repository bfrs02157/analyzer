package com.blurryface.analyzer.core.listener;

import com.blurryface.analyzer.core.entity.AuditData;
import com.blurryface.analyzer.core.entity.interfaces.Auditable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void beforeSave(Auditable auditable){
        AuditData auditData = auditable.getAuditData();
        if(auditData == null){
            auditData = new AuditData();
            auditable.setAuditData(auditData);
        }
        Long userId = 1L;
        auditData.setCreatedAt(LocalDateTime.now());
        auditData.setCreatedBy(ObjectUtils.isEmpty(userId) ? "SYSTEM" : userId.toString());
        auditData.setUpdatedAt(auditData.getCreatedAt());
        auditData.setUpdatedBy(auditData.getCreatedBy());
    }

    @PreUpdate
    public void beforeUpdate(Auditable auditable){
        AuditData auditData = auditable.getAuditData();
        Long userId = 1L;
        auditData.setUpdatedAt(LocalDateTime.now());
        auditData.setUpdatedBy(ObjectUtils.isEmpty(userId) ? "SYSTEM" : userId.toString());
    }
}
