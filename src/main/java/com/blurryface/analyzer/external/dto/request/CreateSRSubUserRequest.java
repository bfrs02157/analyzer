package com.blurryface.analyzer.external.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class CreateSRSubUserRequest implements Serializable {

    @Builder.Default
    @JsonProperty("is_pii_allow")
    private String isPiiAllow = "1";

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @Builder.Default
    @JsonProperty("modules")
    private List<String> modules = List.of("1", "2", "4", "5", "7", "18", "19", "20", "21", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33");

    @Builder.Default
    @JsonProperty("modulesSettings")
    private List<String> modulesSettings = List.of("1203", "1204", "1205", "1206", "1207", "1208", "1209", "1210", "1211", "1212", "1213");

    @Builder.Default
    @JsonProperty("is_web")
    private Integer isWeb = 1;
}
