package com.fristcode.task.ui.addPost

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fristcode.task.R
import com.fristcode.task.model.PostModel
import kotlinx.android.synthetic.main.fragment_add_post.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

class AddPostFragment : Fragment() {

    companion object {
        fun newInstance() = AddPostFragment()
    }

    private var selectPhotoUri: Uri? = null
    private val PICK: Int = 0
    private val viewModel  by inject<AddPostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buAdd.setOnClickListener {
            val title = etAddTitle.text.trim().toString()

            if (title.isNullOrEmpty()){
                etAddTitle.error = "This failed is required"
                return@setOnClickListener
            }
            insertDataToDatabase(title)
        }

        imgPost.setOnClickListener {
            openGalleryAndGetImage()

        }
    }
    private fun insertDataToDatabase(mTitle: String) {
        val post = PostModel().apply {
            this.name = mTitle
            this.image = selectPhotoUri.toString()
        }

        viewModel.addPost(post)
        Toast.makeText(requireContext() , "Post has been added successfully" , Toast.LENGTH_LONG).show()
    }


    private fun openGalleryAndGetImage() {
        val galleyIntent = Intent()
        galleyIntent.type = "image/*"
        galleyIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(galleyIntent, PICK)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectPhotoUri = data.data
            imgPost.setImageURI(selectPhotoUri)

        }
    }

}