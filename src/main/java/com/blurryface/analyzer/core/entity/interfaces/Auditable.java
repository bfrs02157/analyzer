package com.blurryface.analyzer.core.entity.interfaces;


import com.blurryface.analyzer.core.entity.AuditData;

public interface Auditable {

    AuditData getAuditData();

    void setAuditData(AuditData data);

}
