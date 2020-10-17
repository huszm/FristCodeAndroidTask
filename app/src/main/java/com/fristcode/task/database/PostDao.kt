package com.fristcode.task.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fristcode.task.model.PostModel

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(postModel: PostModel)

    @Update
    suspend fun editPost(postModel: PostModel)

    @Delete
    suspend fun deletePost(postModel: PostModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfPosts(listPosts: List<PostModel>)

    @Query("SELECT * FROM posts_table")
    fun readAllData(): LiveData<List<PostModel>>

}