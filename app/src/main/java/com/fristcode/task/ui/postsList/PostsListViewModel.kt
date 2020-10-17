package com.fristcode.task.ui.postsList

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fristcode.task.eventBus.EventProgressBarShowHide
import com.fristcode.task.model.PostModel
import com.fristcode.task.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class PostsListViewModel(context: Context) : ViewModel() {

    var readAllData: LiveData<List<PostModel>>
    private val repository: Repository = Repository(context)

    init {
        readAllData = repository.readAllPost
    }
    private val postsListResponse: MutableLiveData<List<PostModel>> = MutableLiveData()

    fun getPosts(limit: String, page: String) {
        viewModelScope.launch {
            try {
                EventBus.getDefault().postSticky(EventProgressBarShowHide(true))
                val response = repository.getPosts(limit, page)
                EventBus.getDefault().postSticky(EventProgressBarShowHide(false))
                if (response.isSuccessful) {
                    addListPosts(response.body()!!.data)
                    postsListResponse.value = response.body()!!.data
                } else {
                    Log.d("###", response.errorBody().toString())
                }
            } catch (e: Exception) {
                // that's happened when no internet
                EventBus.getDefault().postSticky(EventProgressBarShowHide(false))
            }

        }
    }


    private fun addListPosts(listPosts: List<PostModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addListPosts(listPosts)
        }
    }

    fun deletePost(postModel: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePost(postModel)
        }
    }
}