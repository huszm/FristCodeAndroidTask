package com.fristcode.task.common

import android.util.Log
import com.fristcode.task.model.PostModel

object Common {

    var postModel: PostModel? = null

    fun logString(message: String?) {
        Log.v("###", message.toString())
    }

}