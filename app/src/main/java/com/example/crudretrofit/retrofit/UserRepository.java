package com.example.crudretrofit.retrofit;

import androidx.annotation.NonNull;

import com.example.crudretrofit.user_list.userlist_models.GetAllUsersResponse;
import com.example.crudretrofit.user_list.userlist_models.GetUsersByIdResponse;
import com.example.crudretrofit.login.loginmodels.LoginRequest;
import com.example.crudretrofit.login.loginmodels.LoginResponse;
import com.example.crudretrofit.user.user_models.UserRequest;
import com.example.crudretrofit.user.user_models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final ApiInterface apiInterface;

    public UserRepository() {
        apiInterface = RetrofitClient.getInstance().getApiInterface();
    }

    // Fetch all users
    public void getAllUsers(final UserCallback<GetAllUsersResponse> callback) {
        apiInterface.getAllUser().enqueue(new Callback<GetAllUsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAllUsersResponse> call, @NonNull Response<GetAllUsersResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to fetch users");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAllUsersResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Create a user
    public void createUser(UserRequest userRequest, final UserCallback<UserResponse> callback) {
        apiInterface.createUser(userRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to create user");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Get user by ID
    public void getUserById(int userId, final UserCallback<GetUsersByIdResponse> callback) {
        apiInterface.getItemById(userId).enqueue(new Callback<GetUsersByIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUsersByIdResponse> call, @NonNull Response<GetUsersByIdResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to fetch user");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUsersByIdResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Update a user
    public void updateUser(UserRequest userRequest, final UserCallback<UserResponse> callback) {
        apiInterface.updateItem(userRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to update user");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Delete a user
    public void deleteUser(int userId, final UserCallback<Void> callback) {
        apiInterface.deleteItem(userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Deletion failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Login user
    public void loginUser(LoginRequest loginRequest, final UserCallback<LoginResponse> callback) {
        apiInterface.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Login failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Callback interface
    public interface UserCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }
}
