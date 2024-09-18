package com.example.crudretrofit.user_list.userlist_models;

import com.example.crudretrofit.user.user_models.UserRequest;

import java.util.List;

public class GetAllUsersResponse {

    private boolean status;
    private String message;
    private List<UserRequest> data;

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

    public List<UserRequest> getData() {
        return data;
    }

    public void setData(List<UserRequest> data) {
        this.data = data;
    }

    // Nested Student class
//    public static class Student {
//        private int id;
//        private String name;
//        private String email;
//        private String phone;
//        private String address;
//
//        // Getters and Setters
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//    }
}
