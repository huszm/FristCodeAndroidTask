package com.fristcode.task.ui.addPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fristcode.task.model.PostModel
import com.fristcode.task.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostViewModel(
    private val repository: Repository
) : ViewModel() {

    private val readAllData: LiveData<List<PostModel>> = repository.readAllPost

    fun addPost(postModel: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(postModel)
        }
    }
}