package com.fristcode.task.module

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

val appModule = module {

    single { PostsListFragment() }

    single { AddPostFragment() }

    single { EditPostFragment() }

    single { Repository(get()) }

    viewModel { PostsListViewModel(get()) }

    viewModel { AddPostViewModel(get()) }

    viewModel { EditPostViewModel(get()) }

    viewModel { PostDetailsViewModel(get()) }
}