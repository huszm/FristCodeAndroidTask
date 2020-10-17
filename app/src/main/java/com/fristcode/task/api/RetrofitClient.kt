package com.fristcode.task.api

import com.fristcode.task.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val client: OkHttpClient? = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor {
            val request: Request = it.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("X-Oc-Merchant-Id", "123")
                .addHeader("Content-Type", "application/json")
                .method(it.request().method(), it.request().body())
                .build()
            return@addInterceptor it.proceed(request)
        }.build()


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }

}