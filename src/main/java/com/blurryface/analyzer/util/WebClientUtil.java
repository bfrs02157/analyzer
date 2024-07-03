package com.blurryface.analyzer.util;

import com.blurryface.analyzer.exception.custom.ExternalServiceException;
import com.blurryface.analyzer.exception.custom.RequestBodyRequiredException;
import com.blurryface.analyzer.exception.custom.UnsupportedHttpMethodException;
import com.blurryface.analyzer.helpers.enums.ServiceName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
@Component
public class WebClientUtil {

    private static final List<HttpMethod> httpMethodsWithBody = List.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH);

    private static final List<HttpMethod> httpMethodsWithoutBody = List.of(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.DELETE);

    @Autowired
    private WebClient webClient;

    public <T> T makeExternalCallBlocking(ServiceName serviceName, String baseUri, String endPoint, HttpMethod method, String requestId,
                                          HttpHeaders httpHeaders, Map<String, String> parameters, Object body,
                                          Class<T> responseClass) {
        Mono<T> responseMono = makeExternalCallNonBlocking(serviceName, baseUri, endPoint, method, requestId, httpHeaders, parameters, body, responseClass);
        return responseMono.block();
    }

    public <T> CompletableFuture<T> makeExternalCallCompletableFuture(ServiceName serviceName, String baseUri, String endPoint, HttpMethod method, String requestId,
                                                                      HttpHeaders httpHeaders, Map<String, String> parameters, Object body,
                                                                      Class<T> responseClass) {
        Mono<T> responseMono = makeExternalCallNonBlocking(serviceName, baseUri, endPoint, method, requestId, httpHeaders, parameters, body, responseClass);
        return responseMono.toFuture();
    }

    public <T> Mono<T> makeExternalCallNonBlocking(ServiceName serviceName, String baseUri, String endPoint, HttpMethod method, String requestId,
                                                   HttpHeaders httpHeaders, Map<String, String> parameters, Object body,
                                                   Class<T> responseClass) throws RequestBodyRequiredException, UnsupportedHttpMethodException {

        if(httpMethodsWithBody.contains(method) && body == null){
            throw new RequestBodyRequiredException();
        }

        String uri = constructUri(baseUri, endPoint, parameters);

        if(httpMethodsWithBody.contains(method)) {
            return invokeWebClientWithBody(serviceName, uri, method, requestId, httpHeaders, body, responseClass);
        } else if(httpMethodsWithoutBody.contains(method)) {
            return invokeWebClientWithoutBody(serviceName, uri, method, requestId, httpHeaders, responseClass);
        } else {
            throw new UnsupportedHttpMethodException();
        }
    }

    private <T> Mono<T> invokeWebClientWithoutBody(ServiceName serviceName, String uri, HttpMethod method, String requestId,
                                                   HttpHeaders httpHeaders, Class<T> responseClass) {

//        loggerUtil.logExternalAPIRequest(serviceName, uri, method, requestId, httpHeaders, null);

        if(httpHeaders == null){
            httpHeaders = new HttpHeaders();
            httpHeaders.add("ignore-key", "ignore-value");
        }

        long executionStartTime = System.currentTimeMillis();

        return webClient.method(method)
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .headers(getHeadersConsumer(httpHeaders))
                .exchangeToMono(clientResponse -> bodyToMonoAndLogResponse(serviceName, uri, method, requestId, executionStartTime, clientResponse, responseClass))
                .timeout(Duration.ofSeconds(5), Mono.empty());
    }

    private <T> Mono<T> invokeWebClientWithBody(ServiceName serviceName, String uri, HttpMethod method, String requestId,
                                                HttpHeaders httpHeaders, Object body, Class<T> responseClass) {

//        loggerUtil.logExternalAPIRequest(serviceName, uri, method, requestId, httpHeaders, body);

        if(httpHeaders == null){
            httpHeaders = new HttpHeaders();
            httpHeaders.add("ignore-key", "ignore-value");
        }

        long executionStartTime = System.currentTimeMillis();

        return webClient.method(method)
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .headers(getHeadersConsumer(httpHeaders))
                .bodyValue(body)
                .exchangeToMono(clientResponse -> {
                    System.out.println(clientResponse);
                    return bodyToMonoAndLogResponse(serviceName, uri, method, requestId, executionStartTime, clientResponse, responseClass);
                })
                .timeout(Duration.ofSeconds(5), Mono.empty());
    }



    private <T> Mono<T> bodyToMonoAndLogResponse(ServiceName serviceName, String uri, HttpMethod method, String requestId,
                                                 long executionStartTime, ClientResponse clientResponse, Class<T> responseClass) {

        long latency = System.currentTimeMillis() - executionStartTime;

        if(clientResponse.statusCode().is2xxSuccessful()){
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> {
                        JSONObject responseObject = new JSONObject();
                        try{
                            responseObject = new JSONObject(body);
                        } catch (Exception ignored){

                        }
//                        loggerUtil.logExternalAPIResponse(serviceName, uri, method, requestId,
//                                HttpStatus.valueOf(clientResponse.statusCode().value()), clientResponse.headers().asHttpHeaders(),
//                                responseObject, latency, Level.INFO);
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            return Mono.just(objectMapper.readValue(body, responseClass));
                        } catch (JsonProcessingException e) {
                            log.info("Error processing response: {}", e.getMessage());
                            return Mono.empty();
                        }
                    });
        } else {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(errorBody -> {
                        JSONObject errorObject = new JSONObject();
                        try{
                            errorObject = new JSONObject(errorBody);
                        } catch (Exception ignored){

                        }
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            return Mono.just(objectMapper.readValue(errorBody, responseClass));
                        } catch (JsonProcessingException e) {
                            return Mono.error(new ExternalServiceException(HttpStatus.valueOf(clientResponse.statusCode().value())));
                        }
//                        loggerUtil.logExternalAPIResponse(serviceName, uri, method, requestId,
//                                HttpStatus.valueOf(clientResponse.statusCode().value()), clientResponse.headers().asHttpHeaders(),
//                                errorObject, latency, Level.ERROR);
                    });
        }
    }


    //UTILITY FUNCTIONS

    private Consumer<HttpHeaders> getHeadersConsumer(HttpHeaders httpHeaders){
        return headers -> headers.addAll(httpHeaders);
    }

    private static String constructUri(String baseUri, String endPoint, Map<String, String> parameters) {

        String uri = baseUri + (endPoint != null ? endPoint : "");
        if(CollectionUtils.isEmpty(parameters)) {
            return uri;
        }
        StringBuilder uriBuilder = new StringBuilder(uri);
        uriBuilder.append("?");
        for(Map.Entry<String, String> keyValuePair : parameters.entrySet()) {
            if(keyValuePair.getValue() == null){
                continue;
            }
            String key = URLEncoder.encode(keyValuePair.getKey(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode(keyValuePair.getValue(), StandardCharsets.UTF_8);
            uriBuilder.append(key).append("=").append(value).append("&");
        }
        return uriBuilder.toString();
    }

}
