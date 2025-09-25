package com.agriculture.FarmOracle.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.agriculture.FarmOracle.Model.ProcessingRequest;
import com.agriculture.FarmOracle.Model.UserData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class ProcessingService {
   public String processWithCurl(UserData data) {
    try {
        // Build the JSON payload
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(Map.of(
            "temperature", data.getTemperature(),
            "humidity", data.getSoilMoisture() // mapping as per your requirement
        ));

        // Build HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://crop-predictor-api-0p5u.onrender.com/predict"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
            .build();


        // Send and receive response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    } catch (Exception e) {
        e.printStackTrace();
        return "Error calling crop prediction service: " + e.getMessage();
    }
}
   public String processPricePredictionWithCurl(ProcessingRequest data) {
    try {
        // Prepare JSON payload
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("crop_name", data.getCrop_name());
        jsonNode.put("district_name", data.getDistrict_name());
        jsonNode.put("date", data.getDate());
        String jsonPayload = objectMapper.writeValueAsString(jsonNode);

        // Create HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://crop-predictor-api-0p5u.onrender.com/predict_price"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
            .build();

        // Send request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse response JSON
        return response.body();

    } catch (Exception e) {
        return "hello";
    }
}
    
    
}