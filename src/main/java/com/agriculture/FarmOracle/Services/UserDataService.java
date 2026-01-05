package com.agriculture.FarmOracle.Services;

import com.agriculture.FarmOracle.Model.UserData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserDataService {

    @Autowired
private ProcessingService processingService;


    public List<UserData> getUserData(String userId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        // Accessing the user's data from Firestore
        CollectionReference sensorDataRef = db
                .collection("users_Data")
                .document(userId)
                .collection("sensor_data");

        ApiFuture<QuerySnapshot> future = sensorDataRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<UserData> userDataList = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            UserData data = doc.toObject(UserData.class);
            data.setDocumentIndex(doc.getId());
            userDataList.add(data);

        }

        return userDataList;
    }

public String setAnalyzedAndCreateReport(String userId, UserData userData) throws Exception {
    Firestore db = FirestoreClient.getFirestore();

    String documentIndex = userData.getDocumentIndex();
    if (documentIndex == null || documentIndex.trim().isEmpty()) {
        throw new IllegalArgumentException("documentIndex must not be null or empty");
    }

    // 🔥 CALL ML SERVICE
    String mlResponse = processingService.processWithCurl(userData);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(mlResponse);
    String predictedCrop = root.get("prediction").asText();
    userData.setCrop_name(predictedCrop);

    // Update analyzed flag
    DocumentReference analyzedDocRef = db
            .collection("users_Data")
            .document(userId)
            .collection("analyzed_data")
            .document(documentIndex);

    analyzedDocRef.update("analyzed", true).get();

    // Create report
    Map<String, Object> reportData = new HashMap<>();
    reportData.put("soilMoisture", userData.getSoilMoisture());
    reportData.put("temperature", userData.getTemperature());
    reportData.put("time", userData.getDate());
    reportData.put("crop_name", userData.getCrop_name());
    reportData.put("nitrogen", userData.getNitrogen());
    reportData.put("phosphorus", userData.getPhosphorus());
    reportData.put("potassium", userData.getPotassium());
    reportData.put("pH", userData.getpH());

    db.collection("users_Data")
      .document(userId)
      .collection("reports_data")
      .document(documentIndex)
      .set(reportData)
      .get();

    return "Analyzed and report created successfully";
}


    public Boolean getAnalyzed(String userId, String documentIndex) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference sensorDataRef = db
                .collection("users_Data")
                .document(userId)
                .collection("analyzed_data");

        ApiFuture<QuerySnapshot> future = sensorDataRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot doc : documents) {
            if (doc.getId().equals(documentIndex)) {
                // Set the 'analyzed' field to true
                DocumentSnapshot snapshot = doc.getReference().get().get(); // .get() twice: one for the future, one for
                                                                            // the snapshot
                Boolean analyzed = snapshot.getBoolean("analyzed"); // or snapshot.get("analyzed", Boolean.class);

                return analyzed;
            }
        }

        return false;
    }

    public List<UserData> getUserReports(String userId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        // Accessing the user's data from Firestore
        CollectionReference sensorDataRef = db
                .collection("users_Data")
                .document(userId)
                .collection("reports_data");

        ApiFuture<QuerySnapshot> future = sensorDataRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<UserData> userDataList = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            UserData data = doc.toObject(UserData.class);
            data.setDocumentIndex(doc.getId());
            userDataList.add(data);

        }

        return userDataList;
    }
}
