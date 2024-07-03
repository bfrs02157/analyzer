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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Builder.Default
    @Column(name = "is_main_user")
    private Boolean isMainUser = Boolean.FALSE;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "is_wigzo_admin")
    private String isWigzoAdmin;

    @Column(name = "is_disabled")
    private String isDisabled;

    @Builder.Default
    @Column(name = "sr_email_exists")
    private Boolean srEmailExists = Boolean.FALSE;

    @Column(name = "sr_email_user_id")
    private String srEmailUserId;

    @Builder.Default
    @Column(name = "sr_main_user")
    private Boolean srMainUser = Boolean.FALSE;

    @Builder.Default
    @Column(name = "sr_all_permissions")
    private Boolean srAllPermissions = Boolean.FALSE;

    @Builder.Default
    @Column(name = "sr_mobile_exists")
    private Boolean srMobileExists = Boolean.FALSE;

    @Column(name = "sr_mobile_user_id")
    private String srMobileUserId;

    @Builder.Default
    @Column(name = "email_mobile_mismatch")
    private Boolean emailMobileMismatch = Boolean.FALSE;

    @Column(name = "sr_company_id")
    private Long srCompanyId;

    @Builder.Default
    @Column(name = "is_engage_enabled")
    private Boolean isEngageEnabled = Boolean.FALSE;

    @Builder.Default
    @Column(name = "engage_marketing_enabled")
    private Boolean engageMarketingEnabled = Boolean.FALSE;

    @Column(name = "engage_shop_name")
    private String engageShopName;

    @Column(name = "engage_provider")
    private String engageProvider;

    @Column(name = "engage_business_phone")
    private String engageBusinessPhone;

    @Builder.Default
    @Column(name = "error_data")
    private String errorData = "";

}
