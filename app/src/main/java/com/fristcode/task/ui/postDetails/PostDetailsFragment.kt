package com.fristcode.task.ui.postDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.load
import com.fristcode.task.R
import com.fristcode.task.common.Common
import org.koin.android.ext.android.inject

class PostDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PostDetailsFragment()
    }

    private val viewModel  by inject<PostDetailsViewModel>()

    private lateinit var imgPost: ImageView
    private lateinit var tvTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)
        imgPost = view.findViewById(R.id.imgPost)
        tvTitle = view.findViewById(R.id.tvTitle)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeData()


        //we can use this simple way also to display post details

/*
        tvTitle.text = Common.postModel?.title

        imgPost.load(Uri.parse(Common.postModel?.url))
        Glide.with(requireContext()).load(Uri.parse(Common.postModel?.url)).into(imgPost)
*/

    }

    private fun observeData() {
        viewModel.getPostDetails(Common.postModel?.id.toString())
        viewModel.postDetailsResponse.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                val data = it.body()
                tvTitle.text = data?.name
                imgPost.load(data?.image) {
                    placeholder(R.drawable.ic_launcher_background)
                    error(R.drawable.ic_launcher_foreground)
                    crossfade(true)
                    crossfade(700)
                }

            } else {
                Toast.makeText(requireContext(), it.errorBody().toString(), Toast.LENGTH_LONG)
                    .show()
                Log.d("#### error body", it.errorBody().toString())
            }

        })

    }

}