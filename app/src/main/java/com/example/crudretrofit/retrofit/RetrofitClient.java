package com.example.crudretrofit.retrofit;

import com.example.crudretrofit.retrofit.ApiInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    static RetrofitClient instance=null;
    static ApiInterface apiInterface;
    private RetrofitClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit =new Retrofit.Builder()
               // .baseUrl("http://192.168.208.155:5000") // Replace with your API base URL
                .baseUrl("http://tracking.rrinfrapro.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);


    }
    public synchronized static RetrofitClient getInstance(){
        if(instance==null){
            instance=new RetrofitClient();
        }
        return instance;
    }
    public ApiInterface getApiInterface(){
        return apiInterface;
    }
}
