package com.blurryface.analyzer.external.service;

import com.blurryface.analyzer.exception.custom.CustomException;
import com.blurryface.analyzer.exception.custom.ExternalServiceException;
import com.blurryface.analyzer.external.client.ShiprocketClient;
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
import com.blurryface.analyzer.external.exception.DuplicateUserException;
import com.blurryface.analyzer.external.exception.InvalidEmailUsageException;
import com.blurryface.analyzer.external.exception.InvalidMobileUsageException;
import com.blurryface.analyzer.helpers.enums.CredentialsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.blurryface.analyzer.helpers.enums.ServiceName.SHIPROCKET;


@Service
public class ShiprocketService {

    @Autowired
    private ShiprocketClient shiprocketClient;

    public ValidateEmailResponse validateEmail(String creds) throws DuplicateUserException, InvalidEmailUsageException {
        ValidateEmailResponse validateEmailResponse = shiprocketClient.validateEmail(creds);
        if(validateEmailResponse == null) {
            throw new ExternalServiceException(SHIPROCKET, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return validateEmailResponse;
//        if(Boolean.TRUE.equals(validateEmailResponse.getExists())) {
//            if(Boolean.TRUE.equals(validateEmailResponse.getMainUser()) || Boolean.TRUE.equals(validateEmailResponse.getAllPermission())) {
//                throw new DuplicateUserException(CredentialsType.EMAIL, SHIPROCKET);
//            } else {
//                throw new InvalidEmailUsageException();
//            }
//        }
    }

    public ValidateMobileResponse validateMobileNo(String mobile) throws InvalidMobileUsageException {
        ValidateMobileResponse validateMobileResponse = shiprocketClient.validatePhoneNumber(mobile);
        if(validateMobileResponse == null || Boolean.FALSE.equals(validateMobileResponse.getStatus())) {
            throw new ExternalServiceException(SHIPROCKET, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return validateMobileResponse;
    }

    public InternalTokenUserDetailsResponse getUserDetailsWithInternalToken(String token) {
        try {
            return shiprocketClient.getUserDetailsUsingInternalToken(token);
        } catch (Exception ignored) {
            return null;
        }
    }

    public ApiTokenUserDetailsResponse getUserDetailsUsingApiToken(String token) {
        //TODO: Handle Proper Response
        try {
            return shiprocketClient.getUserDetailsUsingApiToken(token);
        } catch (Exception ignored) {
            return null;
        }
    }

    public String getSrInternalToken(String userId) throws CustomException {
        UserTokenSwitchResponse response = shiprocketClient.getInternalTokenWithSrUserId(userId);
        if(response == null || !Boolean.TRUE.equals(response.getStatus())){
            throw new CustomException("Unable to acquire Shiprocket Token!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response.getTokenDetails().getToken();
    }

    public String getSrInternalTokenFromGoogleCreds(String credentials) throws CustomException {
        try {
            UserTokenSwitchResponse response = shiprocketClient.getInternalTokenWithGoogleCredentials(credentials);
            if(response == null || !Boolean.TRUE.equals(response.getStatus())){
                throw new CustomException("The user already exist on Shiprocket! Unable to acquire Shiprocket Token! Please confirm if you have a valid Shiprocket Account.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response.getTokenDetails().getToken();
        } catch (Exception e) {
            throw new CustomException("The user already exist on Shiprocket! Unable to acquire Shiprocket Token! Please confirm if you have a valid Shiprocket Account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public String createSubUser(CreateSRSubUserRequest subUserRequest, String token) {
        CreateSubUserResponse response = shiprocketClient.createSubUser(subUserRequest, token);
        if(response != null && Boolean.TRUE.equals(response.getSuccess())) {
            return response.getUserId();
        }
        return null;
    }

    public EngageMarketingEnabledChannelDetailsResponse getMarketingEnabledChannelDetails(String token) {
        return shiprocketClient.getMarketingEnabledChannelDetails(token);
    }

    public ProviderDetailsResponse getProviderDetails(GetProviderDetailsRequest request) {
        return shiprocketClient.getProviderDetails(request);
    }

}
