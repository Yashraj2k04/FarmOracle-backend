package com.agriculture.FarmOracle.Controller;

import com.agriculture.FarmOracle.Model.UserData;
import com.agriculture.FarmOracle.Model.UserLoginRequest;
import com.agriculture.FarmOracle.Services.UserDataService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @GetMapping
    public ResponseEntity<?> getUserData(HttpServletRequest request) {
        // Check if the session is valid and the user is logged in
        if (request.getSession(false) == null || request.getSession(false).getAttribute("username") == null) {
            return ResponseEntity.status(401).body("No user is logged in.");
        }

        try {
            // Get user data from the service
            List<UserData> dataList = userDataService
                    .getUserData((String) request.getSession(false).getAttribute("username"));
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user data: " + e.getMessage());
        }
    }

    @PostMapping("/setAnalyzedAndCreateReport")
    public ResponseEntity<?> setAnalyzedTrue(@RequestBody UserData requestBody, HttpServletRequest request)
            throws Exception {
        // Validate session
        if (request.getSession(false) == null || request.getSession(false).getAttribute("username") == null) {
            return ResponseEntity.status(401).body("No user is logged in.");
        }

        String username = (String) request.getSession(false).getAttribute("username");

        // Pass entire UserData object to service now
        String result = userDataService.setAnalyzedAndCreateReport(username, requestBody);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/getAnalyzed")
    public ResponseEntity<?> getAnalyzed(@RequestBody UserData requestBody, HttpServletRequest request)
            throws Exception {
        // get request session
        if (request.getSession(false) == null || request.getSession(false).getAttribute("username") == null) {
            return ResponseEntity.status(401).body("No user is logged in.");
        }

        String username = (String) request.getSession(false).getAttribute("username");
        String documentIndex = requestBody.getDocumentIndex(); // You need a getter here

        Boolean result = userDataService.getAnalyzed(username, documentIndex);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getReports")
    public ResponseEntity<?> getReports(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return ResponseEntity.status(401).body("No user is logged in.");
        }

        String username = (String) session.getAttribute("username");
        System.out.println("Fetching reports for user: " + username); // <-- Log here

        try {
            List<UserData> dataList = userDataService.getUserReports(username);
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user data: " + e.getMessage());
        }
    }

}
