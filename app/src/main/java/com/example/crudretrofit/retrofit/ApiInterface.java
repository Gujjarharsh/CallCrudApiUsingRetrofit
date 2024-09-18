package com.example.crudretrofit.retrofit;

import com.example.crudretrofit.user_list.userlist_models.GetAllUsersResponse;
import com.example.crudretrofit.user_list.userlist_models.GetUsersByIdResponse;
import com.example.crudretrofit.login.loginmodels.LoginRequest;
import com.example.crudretrofit.login.loginmodels.LoginResponse;
import com.example.crudretrofit.user.user_models.UserResponse;
import com.example.crudretrofit.user.user_models.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("/api/getAllStudents")
    Call<GetAllUsersResponse> getAllUser();
    @POST("/api/addStudent")
    Call<UserResponse>createUser(@Body UserRequest userRequest);
    @GET("/api/getStudentById/{id}")
    Call<GetUsersByIdResponse> getItemById(@Path("id") int userId);
    @POST("/api/updateStudent")
    Call<UserResponse> updateItem(@Body UserRequest user);
    @DELETE("/api/deleteStudent/{id}")
    Call<Void> deleteItem(@Path("id") int userId);
    @POST("/api/loginUser")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

}
