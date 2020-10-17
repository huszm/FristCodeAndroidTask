package com.fristcode.task.ui.postDetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.fristcode.task.R
import com.fristcode.task.common.Common
import kotlinx.android.synthetic.main.fragment_post_details.*
import org.koin.android.ext.android.inject

class PostDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PostDetailsFragment()
    }

    private val viewModel by inject<PostDetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.getPostDetails(Common.postModel?.id.toString())
        viewModel.postDetailsResponse.observe(viewLifecycleOwner, Observer {
            tvDetailsTitle.text = it.name
            if (!it.isFromApi)
                Glide.with(requireContext()).load(Uri.parse(it.image)).into(imgPost)
            else
                Glide.with(requireContext()).load(it.image).into(imgPost)
        })

    }

}