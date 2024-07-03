package com.blurryface.analyzer.external.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetProviderDetailsRequest implements Serializable {

    @JsonProperty("orgToken")
    private String orgToken;

    @JsonProperty("businessPhone")
    private String businessPhone;
}
