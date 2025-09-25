package com.agriculture.FarmOracle.Config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    private static final String FIREBASE_JSON = "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"prediction-model-agriculture\",\n" +
        "  \"private_key_id\": \"d6f1910d31dd7af60c9c6ce3a2ea2122417092af\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgWeh5MddvUA54\\nd9WFXjRam699E6aX+cgwgjmYi/YTOrzMkrTdFtuOohlBkAQ99tzqvR9/Z+r7aDWm\\noTcSedC9W9vd/8siGg0YuxrYfSgMzbH3hV4Y0J1Zb2AABp5ufhOMCQ10AcIGyk2/\\nkWOfp8r7atNDxVtO6RGSF7+SlNQGLE1F5xqcak72opl9uMfyU//29/V0XgxTUtns\\nlBjJceJKcOZa9wus1C9jaXIUaQDdc01yHXSHfeVvhwf0a1Gk4SSgCKY+fwQSGC3i\\n5KV2KvmUq3l6ZFoSf+OkAkzwJs+zjD2LTTUw/7ISn9KzpbU9nGl+bzH4ImTc9B/9\\nCpZ+rZHTAgMBAAECggEAF/yLCYIaq3SKzZuDbcFjTICIdknXvFkpiJQaYIbjRwoc\\nzu9NLdtq+YGnDIcqE0jX0Cd9TDYz0OrMR8JK9jERuBnDIIjQjqfd+KqYf/Ts5klo\\ny1GAe27NcuD1t9tFnNqwOtzpzcYri498pODvSaU0eHqI4tTlbT7xGW0DA77OnqOK\\nQGFyO3k6hLB1Fg8dNORkAZn5LU6mbMTWbT0q4M34FsBzSYxyx9C9bXUnVI+wSpOw\\nwB1wn/9MxSlqdp+ylB3F5ACZie8y/Aq7RmLYLCBCfdYfltyCeMu4MpZcKUt74Ro0\\nVMRjjPlH/i9l1mpmIV/ESoOCQ2V+/Uim+nKCHsoYvQKBgQDRBrs2HGekIuWM1xyj\\niS67xB2XJAXa9JLKGFMzrxMTLZWTZqPhkwJUXbQsnTL2yZxfNWMkuAm8HsMfOXtF\\nGEY2dsABFUOchJoy6LUXjtyZKOTJS5jn9OBRx3Qqz5YJlZ/c1QdnGbenPCtYEJHH\\nsIPuLnqS+8/R7OIporCNS5PzvQKBgQDEYubwHALgXHRRMoTQ91DgwpSFh0cpOZWc\\nHq+9+SU4JaE1UOcc4XeTfkiHXpyuS8Y8XBWs4RGrNAKGbSR6mETBv6tkD2Zjib0+\\nhAv/8LjCKO/EHtz1fvR6DSnM2cuQI4kVmS1KIcHVtiE61aBtqwYiBjRKaF8z4PJs\\ngyCwk7MszwKBgD59nbCis1V93VTK5tP5alBWZGZQLbP1FIF43j8AY1qDcUldETea\\nJMMmNHx2Dst7INXR9y7+GmL0a29FVxYkGLGltOdd/RWpCxSXP7SoUEk10Zhgknke\\nW4X3dEJsRzXcVqvFwG3RYXeM5IRyh7LEkdy+ZoTU5Z+kC0VZTyEe7D+dAoGBAIQ4\\ngnvOoDEmjjnM5Zc7q/xnhU+RkNu0pYIDNxeUkvaGf09CNRhAxR1MvNBHZv4Cjmk9\\nJmrK7fZygfC3swPHWSOJRH+NzsXdish4ZwveqsYugZ9tmB7BZyB1lFsTl/ZntngQ\\nNWnf6H1WrnDKO8UajHqD6tjeBJsNLKrabjv0dYIJAoGAa2pXoOCd62s3+zkvpAVK\\n208kDyTUFm+oVUc4IbC6x1WHC7QUp293Wpix9mF5rxHc9Ku3PtQYulspPSDltRFr\\ndfAMDrtJm9U7RIq1wjcvM3VmXdkDc7n7GNhssCOsBws7HcvLV/ygyKi91986Z8CD\\nfuwRPAegwmmShCvjxUA2c4Q=\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"firebase-adminsdk-fbsvc@prediction-model-agriculture.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"114270068626444348804\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fbsvc%40prediction-model-agriculture.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = new ByteArrayInputStream(FIREBASE_JSON.getBytes(StandardCharsets.UTF_8));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("✅ Firebase Initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
