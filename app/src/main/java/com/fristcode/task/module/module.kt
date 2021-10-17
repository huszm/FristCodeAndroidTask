package com.fristcode.task.module

import com.fristcode.task.api.Api
import com.fristcode.task.api.RetrofitClient
import com.fristcode.task.database.PostDao
import com.fristcode.task.database.PostDatabase
import com.fristcode.task.repository.Repository
import com.fristcode.task.ui.addPost.AddPostFragment
import com.fristcode.task.ui.addPost.AddPostViewModel
import com.fristcode.task.ui.editPost.EditPostFragment
import com.fristcode.task.ui.editPost.EditPostViewModel
import com.fristcode.task.ui.postDetails.PostDetailsViewModel
import com.fristcode.task.ui.postsList.PostsListFragment
import com.fristcode.task.ui.postsList.PostsListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module (override = true) {
    single { Repository(get() , get()) }
}


val roomModule = module  (override = true){
    fun provideRoom(): PostDao {
        return PostDatabase.getDatabase().postDao()
    }
    single { provideRoom() }
}

val retrofitServiceModule = module  (override = true){
    fun provideRetrofit(): Api {
        return RetrofitClient.retrofit.create(Api::class.java)
    }
    single { provideRetrofit() }
}

val viewModelModule = module (override = true){

    viewModel { PostsListViewModel(get()) }

    viewModel { AddPostViewModel(get()) }

    viewModel { EditPostViewModel(get()) }

    viewModel { PostDetailsViewModel(get()) }

}