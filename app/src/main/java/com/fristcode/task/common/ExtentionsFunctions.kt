package com.fristcode.task.common

import android.content.Context
import android.content.Intent
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.fristcode.task.R
import com.fristcode.task.utils.DomainIntegration
import org.greenrobot.eventbus.EventBus
import retrofit2.Response

// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    Common.logString(message)
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    Common.logString(message)
}

fun ViewModel.toast(message: String) {
    Toast.makeText(DomainIntegration.getApplication(), message, Toast.LENGTH_SHORT).show()
    Common.logString(message)
}


fun View.hide() {
    this.visibility = View.GONE
}

fun View.hideIf(condition: Boolean) {
    if (condition) {
        this.visibility = View.GONE
    }
}

fun View.showIf(condition: Boolean) {
    if (condition) {
        this.visibility = View.VISIBLE
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.changeVisibility() {
    if (isVisible) hide() else show()
}

fun EditText.checkEmpty(): Boolean {
    val string = text.toString().trim()
    if (string.isNullOrEmpty()) {
        error = DomainIntegration.getApplication().getString(R.string.required)
        return true
    }
    return false
}

fun EditText.limitLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}
fun EditText.checkLength(length: Int): Boolean {
    val string = text.toString().trim()
    if (string.length < length) {
        error = DomainIntegration.getApplication().getString(R.string.required) + " $length"
        return true
    }
    return false
}
