package com.example.crudretrofit.login.loginmodels;

public class LoginResponse {
    private boolean status;
    private String message;
    private LoginUser loginUser;

    // Getters and Setters
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LoginUser getUser() { return loginUser; }
    public void setUser(LoginUser loginUser) { this.loginUser = loginUser; }
}
