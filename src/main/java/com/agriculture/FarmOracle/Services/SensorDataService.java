package com.agriculture.FarmOracle.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agriculture.FarmOracle.Model.SensorData;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
// import com.google.cloud.firestore.v1.FirestoreClient;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class SensorDataService {

    public List<SensorData> getSensorData(String userId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        CollectionReference sensorDataRef = db
                .collection("users_Data")
                .document(userId)
                .collection("sensor_data");

        ApiFuture<QuerySnapshot> future = sensorDataRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<SensorData> result = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            SensorData data = doc.toObject(SensorData.class);
            result.add(data);
        }

        return result;
    }
    
}


