package com.blurryface.analyzer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${app.serviceTag}")
    private String appServiceTag;

    @Value("${app.secret}")
    private String appSecret;

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.admin-user-group-id}")
    private Long adminUserGroupId;

    @Value("${app.no-scope-user-group-id}")
    private Long noScopeUserGroupId;

    //Redis Properties
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Value("${spring.redis.username}")
    private String redisUserName;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.timeout}")
    private String redisTimeout;

    @Value("${spring.redis.useSsl}")
    private Boolean redisUseSsl;

    //Encryption Properties
    @Value("${app.crypto.encryption.rsa.enabled}")
    private Boolean rsaEncryptionEnabled;

    @Value("${app.crypto.encryption.aes.key}")
    private String aesEncryptionKey;

    //Session Properties
    @Value("${app.session.active-device-limit}")
    private Long activeDeviceLimit;

    @Value("${app.session.expiry.authenticated-in-minutes}")
    private Long authenticatedSessionExpiry;

    @Value("${app.session.expiry.anonymous-in-days}")
    private Long guestSessionExpiry;

    @Value("${app.session.expiry.refresh-token-in-days}")
    private Long refreshTokenExpiry;

    //Verification Properties

    @Value("${app.verification.otp.failure-limit}")
    private Integer otpMaxFailureCount;

    @Value("${app.verification.otp.request-limit}")
    private Integer otpMaxRequestCount;

    @Value("${app.cache.password-expiry-in-minutes}")
    private Long updatePasswordCacheExpiry;

    @Value("${app.cache.sr-token-expiry-in-hours}")
    private Long srTokenCacheExpiry;

    //EXTERNAL

    //FRONTEND
    @Value("${app.external.frontend.baseUri}")
    private String frontEndBaseUri;

    @Value("${app.external.frontend.endpoints.user-invitation-validation}")
    private String userInvitationValidationEndpoint;

    @Value("${app.external.frontend.endpoints.verify-email}")
    private String verifyEmailEndpoint;

    @Value("${app.external.frontend.endpoints.switch-apps}")
    private String switchAppsEndpoint;

    //SHIPROCKET
    @Value("${app.external.shiprocket.baseUri}")
    private String shiprocketBaseUri;

    @Value("${app.external.shiprocket.service-name}")
    private String shiprocketServiceName;

    @Value("${app.external.shiprocket.service-secret}")
    private String shiprocketServiceSecret;

    @Value("${app.external.shiprocket.endpoints.validate-email}")
    private String shiprocketValidateEmailEndpoint;

    @Value("${app.external.shiprocket.endpoints.user-details-api-token}")
    private String shiprocketApiTokenUserDetailsEndpoint;

    @Value("${app.external.shiprocket.endpoints.user-details-internal-token}")
    private String shiprocketInternalTokenUserDetailsEndpoint;

    @Value("${app.external.shiprocket.endpoints.switch-token}")
    private String shiprocketSwitchTokenEndPoint;

    @Value("${app.external.shiprocket.endpoints.google-auth-callback}")
    private String shiprocketGetInternalTokenUsingGoogleCredsEndPoint;

    @Value("${app.external.shiprocket.endpoints.create-sub-user}")
    private String shiprocketCreateSubUserEndPoint;

    @Value("${app.external.shiprocket.endpoints.validate-mobile}")
    private String shiprocketValidateMobileEndpoint;

    //SINFINI
    @Value("${app.external.sinfini.enabled}")
    private Boolean sinfiniCommunicationEnabled;

    @Value("${app.external.sinfini.baseUri}")
    private String sinfiniBaseurl;

    @Value("${app.external.sinfini.apiKey}")
    private String sinfiniApiKey;

    @Value("${app.external.sinfini.sms.sender}")
    private String sinfiniSmsSender;

    @Value("${app.external.sinfini.sms.method}")
    private String sinfiniSmsMethod;

    @Value("${app.external.sinfini.sms.templateId}")
    private String sinfiniSmsTemplateId;

    //KALEYRA
    @Value("${app.external.kaleyra.enabled}")
    private Boolean kaleyraCommunicationEnabled;

    @Value("${app.external.kaleyra.baseUri}")
    private String kaleyraBaseurl;

    @Value("${app.external.kaleyra.senderId}")
    private String kaleyraSenderId;

    @Value("${app.external.kaleyra.apiKey}")
    private String kaleyraApiKey;

    @Value("${app.external.kaleyra.fromNo}")
    private String kaleyraFromNo;

    @Value("${app.external.kaleyra.endpoints.send-messages}")
    private String kaleyraSendMessageEndPoint;

    @Value("${app.external.kaleyra.whatsapp.otp-template-name}")
    private String kaleyraWhatsappOtpTemplateName;

    @Value("${app.external.kaleyra.whatsapp.channel}")
    private String kaleyraWhatsappChannel;

    @Value("${app.external.kaleyra.whatsapp.callBackUri}")
    private String kaleyraWhatsappCallbackUri;

    //NETCORE
    @Value("${app.external.netcore.enabled}")
    private Boolean netCoreCommunicationEnabled;

    @Value("${app.external.netcore.baseUri}")
    private String netCoreBaseUri;

    @Value("${app.external.netcore.apiKey}")
    private String netCoreApiKey;

    @Value("${app.external.netcore.fromEmail}")
    private String netCoreFromEmail;

    @Value("${app.external.netcore.fromName}")
    private String netCoreFromName;

    @Value("${app.external.netcore.endpoints.send-mail}")
    private String netCoreSendEmailEndpoint;

    //PATRON
    @Value("${app.external.patron.baseUri}")
    private String patronBaseUri;

    @Value("${app.external.patron.endpoints.tenant-details}")
    private String getTenantDetailsEndpoint;

    @Value("${app.external.patron.endpoints.tenant-details-v2}")
    private String getTenantDetailsV2Endpoint;

    //GOOGLE
    @Value("${app.external.google.oauth-client-id}")
    private String googleOauthClientId;

    //SHOPIFY
    @Value("${app.external.shopify.baseUri}")
    private String shopifyBaseUri;

    @Value("${app.external.shopify.client-id}")
    private String shopifyAppClientId;

    @Value("${app.external.shopify.client-secret}")
    private String shopifyAppClientSecret;

    @Value("${app.external.shopify.oauth-callback-uri}")
    private String shopifyAppOauthCallbackUri;

    @Value("${app.external.shopify.oauth-app-scopes}")
    private String shopifyAppOauthScopes;

    @Value("${app.external.shopify.endpoints.oauth-authorize}")
    private String shopifyOAuthAuthorizeEndpoint;

    @Value("${app.external.shopify.endpoints.oauth-access-token}")
    private String shopifyOAuthAccessTokenEndpoint;
}
