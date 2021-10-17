package com.fristcode.task.ui.postDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fristcode.task.common.Common
import com.fristcode.task.eventBus.EventProgressBarShowHide
import com.fristcode.task.model.PostModel
import com.fristcode.task.repository.Repository
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class PostDetailsViewModel(private val repository: Repository) : ViewModel() {

    val postDetailsResponse: MutableLiveData<PostModel> = MutableLiveData()

    fun getPostDetails(id: String) {
        viewModelScope.launch {
            if (Common.postModel?.isFromApi!!) {
                EventBus.getDefault().postSticky(EventProgressBarShowHide(true))
                val response = repository.getPostDetails(id)
                EventBus.getDefault().postSticky(EventProgressBarShowHide(false))
                if (response.isSuccessful) {
                    postDetailsResponse.value = response.body()?.data
                } else {
                    Log.d("#### error body", response.errorBody().toString())
                }
            } else {
                postDetailsResponse.value = Common.postModel
            }
        }
    }
}