package com.agriculture.FarmOracle.Model;

public class UserLoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // Optionally, you can also include a setter for password if needed
    public void setPassword(String password) {
        this.password = password;
    }
}
