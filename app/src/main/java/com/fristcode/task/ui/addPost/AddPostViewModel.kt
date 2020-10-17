package com.fristcode.task.ui.addPost

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fristcode.task.model.PostModel
import com.fristcode.task.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostViewModel(context: Context) : ViewModel() {

    private val readAllData: LiveData<List<PostModel>>
    private val repository: Repository = Repository(context)

    init {
        readAllData = repository.readAllPost
    }

    fun addPost(postModel: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(postModel)
        }
    }
}