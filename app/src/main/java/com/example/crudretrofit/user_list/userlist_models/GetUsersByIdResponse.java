package com.example.crudretrofit.user_list.userlist_models;

import com.example.crudretrofit.user.user_models.UserRequest;

public class GetUsersByIdResponse {

    private boolean status;
    private String message;
    private UserRequest user;

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

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }


}
