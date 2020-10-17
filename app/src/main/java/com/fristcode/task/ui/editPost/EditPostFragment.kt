package com.fristcode.task.ui.editPost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.fristcode.task.R
import com.fristcode.task.common.Common
import com.fristcode.task.model.PostModel
import kotlinx.android.synthetic.main.fragment_edit_post.*
import org.koin.android.ext.android.inject

class EditPostFragment : Fragment() {

    companion object {
        fun newInstance() = EditPostFragment()
    }

    private var selectPhotoUri: Uri? = null
    private val PICK: Int = 0
    private val viewModel by inject<EditPostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_post, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        etTitle.setText(Common.postModel?.name)
        Glide.with(requireContext()).load(Uri.parse(Common.postModel?.image)).into(imgPost)


        buEdit.setOnClickListener {
            val title = etTitle.text.trim().toString()

            if (title.isNullOrEmpty()) {
                etTitle.error = "This failed is required"
                return@setOnClickListener
            }
            updateData(title)
        }

        imgPost.setOnClickListener {
            openGalleryAndGetImage()

        }

    }

    private fun updateData(mTitle: String) {
        val post = PostModel().apply {
            this.id = Common.postModel?.id
            this.name = mTitle
            this.isFromApi = false
            this.image = selectPhotoUri.toString()
        }

        viewModel.editPost(post)
        Toast.makeText(requireContext(), "Post has been edited successfully", Toast.LENGTH_LONG)
            .show()
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