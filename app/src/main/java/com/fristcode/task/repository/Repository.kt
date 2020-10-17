package com.fristcode.task.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.fristcode.task.api.RetrofitClient
import com.fristcode.task.database.PostDao
import com.fristcode.task.database.PostDatabase
import com.fristcode.task.model.ApiResponse
import com.fristcode.task.model.PostModel
import retrofit2.Response

class Repository(context: Context) {

    private val postDao = PostDatabase.getDatabase(context).postDao()
    val readAllPost: LiveData<List<PostModel>> = postDao.readAllData()

    suspend fun editPost(postModel: PostModel){
        postDao.editPost(postModel)
    }
    suspend fun deletePost(postModel: PostModel){
        postDao.deletePost(postModel)
    }
    suspend fun addPost(postModel: PostModel){
        postDao.addPost(postModel)
    }
    suspend fun addListPosts(listPosts: List<PostModel>){
        postDao.addListOfPosts(listPosts)
    }

    suspend fun getPosts(limit:String , page:String): Response<ApiResponse> {
        return RetrofitClient.api.getPosts(limit, page)
    }

    suspend fun getPostDetails(id: String): Response<PostModel> {
        return RetrofitClient.api.getPostDetails(id)
    }


}