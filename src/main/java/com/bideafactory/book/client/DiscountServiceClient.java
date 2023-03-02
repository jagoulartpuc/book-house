package com.bideafactory.book.client;

import com.bideafactory.book.domain.request.DiscountRequest;
import com.bideafactory.book.domain.response.DiscountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class DiscountServiceClient {

    private final String URL = "https://622271e2666291106a26a17c.mockapi.io/discount/v1/new";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DiscountResponse postDiscount(DiscountRequest discountRequest) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(discountRequest))).build();
        var response= client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), DiscountResponse.class);
    }
}