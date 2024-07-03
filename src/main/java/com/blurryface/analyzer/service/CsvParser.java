package com.blurryface.analyzer.service;

import com.blurryface.analyzer.entity.model.Organization;
import com.blurryface.analyzer.entity.model.User;
import com.blurryface.analyzer.entity.repo.OrganizationRepo;
import com.blurryface.analyzer.entity.repo.UserRepo;
import com.blurryface.analyzer.external.dto.response.engage.EngageMarketingEnabledChannelDetailsResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.InternalTokenUserDetailsResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.ValidateEmailResponse;
import com.blurryface.analyzer.external.dto.response.shiprocket.ValidateMobileResponse;
import com.blurryface.analyzer.external.service.ShiprocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CsvParser {

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShiprocketService shiprocketService;

    public void populateOrg(Path filePath) throws IOException, CsvValidationException {
        boolean process = false;
        List<String[]> unprocessedRows = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    if(!process) {
                        process = true;
                        continue;
                    }
                    if(line.length != 10) {
                        log.info("Skipping the row! Invalid number of columns: " + line.length);
                        log.info(Arrays.toString(line));
                        unprocessedRows.add(line);
                    } else {
                        log.info("Processing Start...");
                        Boolean createOrg = Boolean.FALSE;

                        Organization organization = Organization.builder()
                                .orgId(Long.parseLong(line[0]))
                                .orgName(line[1])
                                .domain(line[2])
                                .orgToken(line[3])
                                .orgType(line[4])
                                .channel(line[5])
                                .shopName(line[6])
                                .provider(line[7])
                                .businessPhone(line[8])
                                .wabaId(line[9])
                                .build();

                        organizationRepo.save(organization);

                    }
                }
            }
        }
        log.info("Unprocessed Rows: {}", objectMapper.writeValueAsString(unprocessedRows));
    }


    public void populateUser(Path filePath) throws IOException, CsvValidationException {
        boolean process = false;
        List<String[]> unprocessedRows = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    if(!process) {
                        process = true;
                        continue;
                    }
                    if(line.length != 10) {
                        log.info("Skipping the row! Invalid number of columns: " + line.length);
                        log.info(Arrays.toString(line));
                        unprocessedRows.add(line);
                    } else {
                        log.info("Processing Start...");
                        User user = User.builder()
                                .orgId(Long.parseLong(line[0]))
                                .userId(Long.parseLong(line[1]))
                                .email(line[2])
                                .mobile(line[3])
                                .firstName(line[4])
                                .lastName(line[5])
                                .fullName(line[6])
                                .isMainUser(Boolean.FALSE)
                                .organizationId(line[7])
                                .isWigzoAdmin(line[8])
                                .isDisabled(line[9])
                                .build();
                        try {
                            log.info("Validating Email for {}", user.getEmail());
                            ValidateEmailResponse emailResponse = shiprocketService.validateEmail(user.getEmail());
                            log.info("Validation Response for Email: {}", objectMapper.writeValueAsString(emailResponse));
                            user.setSrEmailExists(emailResponse.getExists());
                            user.setSrEmailUserId(emailResponse.getUserId());
                            user.setSrMainUser(emailResponse.getMainUser());
                            user.setSrAllPermissions(emailResponse.getAllPermission());

                        } catch (Exception e) {
                            user.setErrorData(user.getErrorData() + "| EMAIL_VALIDATION_FAILED ");
                            log.error("Unable to validate email. Exception: {}", e.getMessage());
                        }

                        try {
                            if(!"MISSING".equalsIgnoreCase(user.getMobile())) {
                                String mobile = user.getMobile().replace("+91", "");
                                if(mobile.length() > 10) {
                                    mobile = user.getMobile().replaceFirst("91", "");
                                }
                                if(mobile.length()==10) {
                                    log.info("Validating Mobile for {}", user.getEmail());
                                    ValidateMobileResponse validateMobileResponse = shiprocketService.validateMobileNo(user.getMobile());
                                    log.info("Validation Response for Mobile: {}", objectMapper.writeValueAsString(validateMobileResponse));
                                    user.setSrMobileExists(validateMobileResponse.getExists());
                                    user.setSrMobileUserId(validateMobileResponse.getUserId());
                                    checkEmailMobileMismatch(user);
                                }
                            }
                        } catch (Exception e) {
                            user.setErrorData(user.getErrorData() + " | MOBILE_VALIDATION_FAILED");
                            log.error("Unable to validate mobile. Exception: {}", e.getMessage());
                        }

                        if(Boolean.TRUE.equals(user.getSrEmailExists())) {
                            String token = null;
                            try {
                                log.info("Acquiring Token for User Id {}", user.getUserId());
                                token = shiprocketService.getSrInternalToken(String.valueOf(user.getSrEmailUserId()));
                                log.info("Token for User Id {} is {}", user.getUserId(), token);
                            } catch (Exception e) {
                                user.setErrorData(user.getErrorData() + " | SR_TOKEN_ACQUISITION_FAILED");
                                log.error("Unable to acquire SR Token. Exception: {}", e.getMessage());
                            }

                            if(StringUtils.hasText(token)) {
                                try {
                                    log.info("Fetching UserDetails for User Id {}, Token: {}", user.getUserId(), token);
                                    InternalTokenUserDetailsResponse userDetailsResponse = shiprocketService.getUserDetailsWithInternalToken(token);
                                    log.info("UserDetails for User Id {}, Token: {}, Details: {}", user.getUserId(), token, objectMapper.writeValueAsString(userDetailsResponse));
                                    user.setSrCompanyId(Long.parseLong(userDetailsResponse.getCompanyId()));
                                } catch (Exception e) {
                                    user.setErrorData(user.getErrorData() + " | FETCH_USER_DETAILS_FAILED");
                                    log.error("Unable to get User Details. Exception: {}", e.getMessage());
                                }

                                try {
                                    log.info("Fetching ChannelDetails for User Id {}, Token: {}", user.getUserId(), token);
                                    EngageMarketingEnabledChannelDetailsResponse channelDetailsResponse = shiprocketService.getMarketingEnabledChannelDetails(token);
                                    log.info("ChannelDetails for User Id {}, Token: {}, Details: {}", user.getUserId(), token, objectMapper.writeValueAsString(channelDetailsResponse));
                                    user.setIsEngageEnabled(channelDetailsResponse.getData().getIsEngageEnabled());
                                    user.setEngageMarketingEnabled(channelDetailsResponse.getData().getIsEngageMarketingEnabled());
                                    user.setEngageShopName(channelDetailsResponse.getData().getChannelData() != null && channelDetailsResponse.getData().getChannelData().getAuth() != null ?
                                            channelDetailsResponse.getData().getChannelData().getAuth().getStoreUrl() : null);
                                    user.setEngageProvider(channelDetailsResponse.getData().getProvider());
                                    user.setEngageBusinessPhone(channelDetailsResponse.getData().getWaNumber());

//                                    String shopName = user.getSto().trim().replace("https://", "").replace("http://", "").replace("/", "").trim();
//                                    String engageShopName = StringUtils.hasText(organization.getEngageShopName()) ? organization.getEngageShopName().trim().replace("https://", "").replace("http://", "").replace("/", "").trim() : "";
//                                    String businessPhone = StringUtils.hasText(organization.getBusinessPhone()) ? organization.getBusinessPhone().trim() : "";
//                                    String engageBusinessPhone = StringUtils.hasText(organization.getEngageBusinessPhone()) ? organization.getEngageBusinessPhone().trim() : "";
//
//                                    user.setIsShopSame(shopName.equalsIgnoreCase(engageShopName));
//                                    user.setIsSameNumber(businessPhone.equalsIgnoreCase(engageBusinessPhone));

                                } catch (Exception e) {
                                    user.setErrorData(user.getErrorData() + " | FETCH_CHANNEL_DETAILS_FAILED");
                                    log.error("Unable to fetch channels. Exception: {}", e.getMessage());
                                }
                            }
                        }
                        userRepo.save(user);
                    }
                }
            }
        }
        log.info("Unprocessed Rows: {}", objectMapper.writeValueAsString(unprocessedRows));
    }


    public void mapMainUser(Path filePath) throws IOException, CsvValidationException {
        boolean process = false;
        List<String[]> unprocessedRows = new ArrayList<>();
        List<Long> notFoundOrgs = new ArrayList<>();
        List<Long> userNotFoundOrgs = new ArrayList<>();
        List<Long> userMismatchOrgs = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    if(!process) {
                        process = true;
                        continue;
                    }
                    if(line.length != 3) {
                        log.info("Skipping the row! Invalid number of columns: " + line.length);
                        log.info(Arrays.toString(line));
                        unprocessedRows.add(line);
                    } else {
                        log.info("Processing Start...");

                        Optional<Organization> organization = organizationRepo.findByOrgId(Long.parseLong(line[0]));
                        if(organization.isPresent()) {
                            List<User> userList = userRepo.findByEmail(line[2].trim());
                            if(!CollectionUtils.isEmpty(userList)) {
                                Boolean foundMainUser = Boolean.FALSE;
                                for (User user : userList) {
                                    if(user.getOrgId().equals(organization.get().getOrgId())) {
                                        foundMainUser = Boolean.TRUE;
                                        user.setIsMainUser(Boolean.TRUE);
                                        userRepo.save(user);
                                    }
                                }
                                if(!Boolean.TRUE.equals(foundMainUser)) {
                                    userMismatchOrgs.add(Long.parseLong(line[0]));
                                    log.info("User Org mismatch. Email: {}", line[2].trim());
                                }
                            } else {
                                userNotFoundOrgs.add(Long.parseLong(line[0]));
                                log.info("User not found. Email: {}", line[2].trim());
                            }
                        } else {
                            notFoundOrgs.add(Long.parseLong(line[0]));
                            log.info("Org not present. OrgId: {}", Long.parseLong(line[0]));
                        }
                    }
                }
            }
        }
        log.info("Not Found Orgs: {}", objectMapper.writeValueAsString(notFoundOrgs));
        log.info("Not Found Users: {}", objectMapper.writeValueAsString(userNotFoundOrgs));
        log.info("User Mismatch: {}", objectMapper.writeValueAsString(userMismatchOrgs));
    }

    private void checkEmailMobileMismatch(User user) {
        if(Boolean.TRUE.equals(user.getSrEmailExists()) && user.getSrEmailUserId() != null) {
            if(Boolean.TRUE.equals(user.getSrMobileExists()) && user.getSrMobileUserId() != null) {
                user.setEmailMobileMismatch(!user.getSrEmailUserId().equalsIgnoreCase(user.getSrMobileUserId()));
            }
        } else {
            if(Boolean.TRUE.equals(user.getSrMobileExists()) && user.getSrMobileUserId() != null) {
                user.setEmailMobileMismatch(Boolean.TRUE);
            }
        }
    }

}
