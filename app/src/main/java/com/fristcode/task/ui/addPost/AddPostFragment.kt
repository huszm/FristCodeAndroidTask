package com.fristcode.task.ui.addPost

import android.app.Activity
<<<<<<< HEAD
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
=======
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fristcode.task.R
import com.fristcode.task.common.checkEmpty
import com.fristcode.task.common.toast
import com.fristcode.task.databinding.FragmentAddPostBinding
import com.fristcode.task.model.PostModel
import org.koin.android.ext.android.inject

class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private var selectPhotoUri: Uri? = null
    private val PICK_IMAGE: Int = 0

    private val viewModel by inject<AddPostViewModel>()
>>>>>>> eef7cce (<message>)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
<<<<<<< HEAD
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
=======
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {

        binding.buAdd.setOnClickListener {
            val title = binding.etAddTitle.text.trim().toString()

            if (binding.etAddTitle.checkEmpty()) {
                return@setOnClickListener
            }

            insertDataToDatabase(title)
        }

        binding.imgPost.setOnClickListener {
            openGalleryAndGetImage()

        }

    }

>>>>>>> eef7cce (<message>)
    private fun insertDataToDatabase(mTitle: String) {
        val post = PostModel().apply {
            this.name = mTitle
            this.image = selectPhotoUri.toString()
            this.isFromApi = false
        }

        viewModel.addPost(post)
<<<<<<< HEAD
        Toast.makeText(requireContext() , "Post has been added successfully" , Toast.LENGTH_LONG).show()
=======
        toast(getString(R.string.added))
>>>>>>> eef7cce (<message>)
    }


    private fun openGalleryAndGetImage() {
        val galleyIntent = Intent()
        galleyIntent.type = "image/*"
        galleyIntent.action = Intent.ACTION_GET_CONTENT
<<<<<<< HEAD
        startActivityForResult(galleyIntent, PICK)
=======
        startActivityForResult(galleyIntent, PICK_IMAGE)
>>>>>>> eef7cce (<message>)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectPhotoUri = data.data
<<<<<<< HEAD
            imgPost.setImageURI(selectPhotoUri)
=======
            binding.imgPost.setImageURI(selectPhotoUri)
>>>>>>> eef7cce (<message>)

        }
    }

}