package com.agriculture.FarmOracle.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriculture.FarmOracle.Model.SensorData;
import com.agriculture.FarmOracle.Services.SensorDataService;

@RestController
@RequestMapping("/api/sensor")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getSensorData(@PathVariable String userId) {
        try {
            List<SensorData> dataList = sensorDataService.getSensorData(userId);
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            e.printStackTrace(); // Show full error in console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching sensor data: " + e.getMessage());
        }
    }
}
