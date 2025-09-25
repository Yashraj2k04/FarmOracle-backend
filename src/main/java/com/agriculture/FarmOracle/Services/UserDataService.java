package com.agriculture.FarmOracle.Services;

import com.agriculture.FarmOracle.Model.UserData;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDataService {

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

        // Reference to the analyzed document to update
        DocumentReference analyzedDocRef = db
                .collection("users_Data")
                .document(userId)
                .collection("analyzed_data")
                .document(documentIndex);

        // Update the 'analyzed' field to true
        ApiFuture<WriteResult> updateFuture = analyzedDocRef.update("analyzed", true);
        WriteResult updateResult = updateFuture.get();

        // Prepare report data from userData fields
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("soilMoisture", userData.getSoilMoisture());
        reportData.put("temperature", userData.getTemperature());
        reportData.put("time", userData.getDate());
        reportData.put("crop_name", userData.getCrop_name());
        reportData.put("price", userData.getPrice());
        reportData.put("district", userData.getDistrict());
        reportData.put("state", userData.getState());
        reportData.put("predictedOnDate", userData.getPredictedOnDate());

        // Create or overwrite the report document with the same documentIndex
        DocumentReference reportDocRef = db
                .collection("users_Data")
                .document(userId)
                .collection("reports_data")
                .document(documentIndex);

        ApiFuture<WriteResult> reportWriteFuture = reportDocRef.set(reportData);
        WriteResult reportWriteResult = reportWriteFuture.get();

        return "Analyzed updated at: " + updateResult.getUpdateTime() +
                ", Report created at: " + reportWriteResult.getUpdateTime();
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
