package com.blurryface.analyzer.entity.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "domain")
    private String domain;

    @Column(name = "org_token")
    private String orgToken;

    @Column(name = "org_type")
    private String orgType;

    @Column(name = "channel")
    private String channel;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "provider")
    private String provider;

    @Column(name = "business_phone")
    private String businessPhone;

    @Column(name = "waba_id")
    private String wabaId;

}
