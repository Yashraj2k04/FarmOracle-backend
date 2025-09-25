package com.agriculture.FarmOracle.Controller;

import com.agriculture.FarmOracle.Model.UserLoginRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request, HttpSession session) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            DocumentReference docRef = db.collection("users").document(request.getUsername());
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                String storedPassword = document.getString("password");
                if (storedPassword != null && storedPassword.equals(request.getPassword())) {
                    session.setAttribute("username", request.getUsername());
                    session.setMaxInactiveInterval(7 * 24 * 60 * 60);                    
                    return ResponseEntity.ok(Map.of(
                        "token", session.getId(), //
                        "username", request.getUsername()
                    ));
                } else {
                    return ResponseEntity.status(401).body("Incorrect password");
                }
            } else {
                return ResponseEntity.status(401).body("User not found");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
    
        // Check if the session is not null and the user attribute exists
        if (session != null && session.getAttribute("username") != null) {
            String user = (String) session.getAttribute("username");
            return ResponseEntity.ok("Logged in as: " + user);
        } else {
            return ResponseEntity.status(401).body("Not logged in");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false); // Don't create a new session
    if(session == null){
        return ResponseEntity.ok("Not Logged In");
    }
    if (session != null) {
        session.invalidate();
    }
    return ResponseEntity.ok("Logged out successfully");
}

}
