package com.fristcode.task.ui.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fristcode.task.repository.Repository
import com.fristcode.task.ui.postsList.PostsListViewModel

class PostDetailsViewModelFactory (private val repository: Repository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailsViewModel(repository) as T
    }
}