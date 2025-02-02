package com.blurryface.analyzer.external.dto.response.shiprocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSubUserResponse implements Serializable {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("user_id")
    private String userId;
}
