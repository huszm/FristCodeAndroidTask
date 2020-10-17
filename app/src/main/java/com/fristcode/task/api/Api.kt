package com.fristcode.task.api

import com.fristcode.task.model.ApiResponse
import com.fristcode.task.model.PostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("products/limit/{limit}/page/{page}")
    suspend fun getPosts(@Path("limit") limit: String , @Path("page") page: String ): Response<ApiResponse>

    @GET("products/{id}")
    suspend fun getPostDetails(@Path("id") id: String): Response<PostModel>

}