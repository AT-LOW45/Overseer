package com.k.utilities;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.function.Consumer;

public class APIService {

    private final String endpoint;
    private String container;
    HttpClient client = HttpClient.newHttpClient();

    public APIService(String endpoint) {
        this.endpoint = endpoint;
    }

    public APIService sendRequest(String add, boolean requiredArgs) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::setBody)
                .join();

        return this;
    }

    private String setBody(String responseBody) {
        container = responseBody;
        return container;
    }

    public void serveRequest(Consumer<String> response) {
        response.accept(container);
    }

}
