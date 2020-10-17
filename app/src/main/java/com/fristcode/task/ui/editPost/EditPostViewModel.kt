package com.fristcode.task.ui.editPost

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fristcode.task.model.PostModel
import com.fristcode.task.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPostViewModel (context: Context): ViewModel() {

    private val repository: Repository = Repository(context)


    fun editPost(postModel: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editPost(postModel)
        }
    }

}