package com.agriculture.FarmOracle.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriculture.FarmOracle.Model.ProcessingRequest;
import com.agriculture.FarmOracle.Model.UserData;
import com.agriculture.FarmOracle.Services.ProcessingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/process")
public class ProcessingController {
    private final ProcessingService processingService;

    public ProcessingController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/userdata")
    public ResponseEntity<?> processUserData(HttpServletRequest request, @RequestBody UserData data) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        String result = processingService.processWithCurl(data);

        return ResponseEntity.ok(result);
    }
    @PostMapping("/price")
    public ResponseEntity<?> processPricePrediction(HttpServletRequest request, @RequestBody ProcessingRequest data) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        String result = processingService.processPricePredictionWithCurl(data);

        return ResponseEntity.ok(result);
    }

}
