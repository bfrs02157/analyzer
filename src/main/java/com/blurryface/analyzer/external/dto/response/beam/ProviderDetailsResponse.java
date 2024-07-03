package com.blurryface.analyzer.external.dto.response.beam;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProviderDetailsResponse implements Serializable {

    @JsonProperty("orgToken")
    private String orgToken;

    @JsonProperty("businessPhone")
    private String businessPhone;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("account")
    private String account;

    @JsonProperty("auth")
    private String auth;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("orgId")
    private int orgId;

    @JsonProperty("id")
    private int id;
}