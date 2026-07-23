package com.nettv.livestore.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/* JADX INFO: loaded from: classes2.dex */
public class RetroClass {
    public static APIService getAPIService(String str) {
        return (APIService) getRetrofitInstance(str).create(APIService.class);
    }

    private static Retrofit getRetrofitInstance(String str) {
        Gson gsonCreate = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        return new Retrofit.Builder().baseUrl(str).client(builder.connectTimeout(60L, timeUnit).readTimeout(60L, timeUnit).writeTimeout(60L, timeUnit).addInterceptor(httpLoggingInterceptor).build()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gsonCreate)).build();
    }
}
