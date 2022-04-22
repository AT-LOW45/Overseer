package com.k.utilities;


import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class APIService {

    private String baseURL;
    private String queriedURI = "";
    private String responseBody;
    private final Map<String, String> urlParams = new HashMap<>();
    private final HttpClient client = HttpClient.newHttpClient();

    public APIService(String endpoint) {
        this.baseURL = endpoint;
    }

    public APIService sendRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(queriedURI.isEmpty() ? baseURL : queriedURI))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::setBody)
                .join();

        return this;
    }

    public void buildURI() {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder = uriBuilder.setScheme("https").setHost(baseURL);

        if(!urlParams.isEmpty()) {
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                uriBuilder = uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
            this.queriedURI = uriBuilder.toString();
        } else {
            this.baseURL = uriBuilder.toString();
        }
    }

    public APIService setParam(String key, String value) {
        urlParams.put(key, value);
        return this;
    }

    private String setBody(String responseBody) {
        this.responseBody = responseBody;
        return responseBody;
    }

    public void serveRequest(Consumer<String> response) {
        if(!baseURL.isEmpty() && !responseBody.isEmpty()) {
            response.accept(responseBody);
        }
    }

}
