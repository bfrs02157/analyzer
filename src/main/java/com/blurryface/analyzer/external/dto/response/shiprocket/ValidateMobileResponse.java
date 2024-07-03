package com.blurryface.analyzer.external.dto.response.shiprocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateMobileResponse {

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("exists")
    private Boolean exists;

    @JsonProperty("user_id")
    private String userId;
}
