package com.blurryface.analyzer.external.client;

import com.blurryface.analyzer.config.AppProperties;
import com.blurryface.analyzer.external.dto.request.CreateSRSubUserRequest;
import com.blurryface.analyzer.external.dto.request.GetProviderDetailsRequest;
import com.blurryface.analyzer.external.dto.response.beam.ProviderDetailsResponse;
import com.blurryface.analyzer.external.dto.response.engage.EngageMarketingEnabledChannelDetailsResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.ApiTokenUserDetailsResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.CreateSubUserResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.InternalTokenUserDetailsResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.UserTokenSwitchResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.ValidateEmailResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.ValidateMobileResponse;
import com.blurryface.analyzer.util.WebClientUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.blurryface.analyzer.helpers.enums.ServiceName.SHIPROCKET;

@Component
public class ShiprocketClient {

    @Autowired
    private WebClientUtil webClientUtil;

    @Autowired
    private AppProperties appProperties;

    public ValidateEmailResponse validateEmail(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("service-name", appProperties.getShiprocketServiceName());
        headers.set("service-secret", appProperties.getShiprocketServiceSecret());
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("email", email);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketValidateEmailEndpoint(), HttpMethod.GET, UUID.randomUUID().toString(),
                headers, queryParam, null, ValidateEmailResponse.class);
    }

    public ValidateMobileResponse validatePhoneNumber(String phoneNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("service-name", appProperties.getShiprocketServiceName());
        headers.set("service-secret", appProperties.getShiprocketServiceSecret());
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("phone", phoneNumber);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketValidateMobileEndpoint(), HttpMethod.GET, UUID.randomUUID().toString(),
                headers, queryParam, null, ValidateMobileResponse.class);
    }


    public ApiTokenUserDetailsResponse getUserDetailsUsingApiToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketApiTokenUserDetailsEndpoint(), HttpMethod.GET, UUID.randomUUID().toString(),
                headers, null, null, ApiTokenUserDetailsResponse.class);
    }

    public InternalTokenUserDetailsResponse getUserDetailsUsingInternalToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HashMap<String, String> params = new HashMap<>();
        params.put("is_web", "1");
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketInternalTokenUserDetailsEndpoint(), HttpMethod.GET, UUID.randomUUID().toString(),
                headers, params, null, InternalTokenUserDetailsResponse.class);
    }


    public UserTokenSwitchResponse getInternalTokenWithSrUserId(String srUserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("service-name", appProperties.getShiprocketServiceName());
        headers.set("service-secret", appProperties.getShiprocketServiceSecret());

        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("user_id", srUserId);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketSwitchTokenEndPoint(), HttpMethod.GET, UUID.randomUUID().toString(),
                headers, queryParam, null, UserTokenSwitchResponse.class);
    }

    public UserTokenSwitchResponse getInternalTokenWithGoogleCredentials(String credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("service-name", appProperties.getShiprocketServiceName());
        headers.set("service-secret", appProperties.getShiprocketServiceSecret());
        Map<String, String> body = new HashMap<>();
        body.put("token", credentials);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketGetInternalTokenUsingGoogleCredsEndPoint(), HttpMethod.POST, UUID.randomUUID().toString(),
                headers, null, body, UserTokenSwitchResponse.class);
    }


    public CreateSubUserResponse createSubUser(CreateSRSubUserRequest srSubUserRequest, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, appProperties.getShiprocketBaseUri(),
                appProperties.getShiprocketCreateSubUserEndPoint(), HttpMethod.POST, UUID.randomUUID().toString(),
                headers, null, srSubUserRequest, CreateSubUserResponse.class);
    }

    public EngageMarketingEnabledChannelDetailsResponse getMarketingEnabledChannelDetails(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, "https://sr-engage-apiv2.shiprocket.in",
                "/wigzo/marketing/channel", HttpMethod.GET, UUID.randomUUID().toString(),
                headers, null, null, EngageMarketingEnabledChannelDetailsResponse.class);
    }

    public ProviderDetailsResponse getProviderDetails(GetProviderDetailsRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "1624e28d-5a10-41c5-ac89-41f5aa76f5b7");
        return webClientUtil.makeExternalCallBlocking(SHIPROCKET, "https://beam-prod.wigzopush.com",
                "/api/v1/provider-credentials/fetch", HttpMethod.POST, UUID.randomUUID().toString(),
                headers, null, request, ProviderDetailsResponse.class);
    }
}
