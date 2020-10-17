package com.fristcode.task.ui.postDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fristcode.task.eventBus.EventProgressBarShowHide
import com.fristcode.task.model.PostModel
import com.fristcode.task.repository.Repository
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import retrofit2.Response

class PostDetailsViewModel(private val repository: Repository) : ViewModel() {

    val postDetailsResponse : MutableLiveData<Response<PostModel>> = MutableLiveData()

    fun getPostDetails(id:String){
        viewModelScope.launch {
            EventBus.getDefault().postSticky(EventProgressBarShowHide(true))
            val response = repository.getPostDetails(id)
            EventBus.getDefault().postSticky(EventProgressBarShowHide(false))
            postDetailsResponse.value = response
        }
    }
}