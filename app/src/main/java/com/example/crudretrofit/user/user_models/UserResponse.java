package com.example.crudretrofit.user.user_models;

public class UserResponse {

    private boolean status;
    private String message;
    private UserRequest data;

    // Getters and Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserRequest getData() {
        return data;
    }

    public void setData(UserRequest data) {
        this.data = data;
    }


}
