package com.blurryface.analyzer.external.dto.response.engage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EngageMarketingEnabledChannelDetailsResponse implements Serializable {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("error")
    private String error;

    @JsonProperty("data")
    private MarketingChannelData data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarketingChannelData implements Serializable {

        @JsonProperty("isEngageEnabled")
        private Boolean isEngageEnabled;

        @JsonProperty("isEngageMarketingEnabled")
        private Boolean isEngageMarketingEnabled;

        @JsonProperty("waNumber")
        private String waNumber;

        @JsonProperty("provider")
        private String provider;

        @JsonProperty("channelData")
        private ChannelData channelData;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelData implements Serializable {

            @JsonProperty("auth")
            private Auth auth;

            @JsonProperty("base_channel_code")
            private String baseChannelCode;

            @JsonProperty("brand_name")
            private String brandName;

            @JsonProperty("id")
            private String id;

            @JsonProperty("name")
            private String name;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Auth implements Serializable {
                @JsonProperty("api_key")
                private String apiKey;

                @JsonProperty("api_password")
                private String apiPassword;

                @JsonProperty("shared_secret")
                private String sharedSecret;

                @JsonProperty("store_url")
                private String storeUrl;
            }

        }

    }

}
