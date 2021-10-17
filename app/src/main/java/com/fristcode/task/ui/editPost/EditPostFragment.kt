package com.fristcode.task.ui.editPost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.fristcode.task.R
import com.fristcode.task.common.Common
import com.fristcode.task.common.checkEmpty
import com.fristcode.task.common.toast
import com.fristcode.task.databinding.FragmentEditPostBinding
import com.fristcode.task.model.PostModel
import org.koin.android.ext.android.inject

class EditPostFragment : Fragment() {

    private var _binding: FragmentEditPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<EditPostViewModel>()

    private var selectPhotoUri: Uri? = null
    private val PICK: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.etTitle.setText(Common.postModel?.name)
        Glide.with(requireContext()).load(Uri.parse(Common.postModel?.image)).into(binding.imgPost)


        binding.buEdit.setOnClickListener {
            if (binding.etTitle.checkEmpty()) {
                return@setOnClickListener
            }

            val title = binding.etTitle.text.trim().toString()
            updateData(title)
        }

        binding.imgPost.setOnClickListener {
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
        toast(getString(R.string.edited))
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
            binding.imgPost.setImageURI(selectPhotoUri)

        }
    }
}