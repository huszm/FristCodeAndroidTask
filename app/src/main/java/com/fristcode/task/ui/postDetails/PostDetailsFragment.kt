package com.fristcode.task.ui.postDetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.fristcode.task.common.Common
import com.fristcode.task.databinding.FragmentPostDetailsBinding
import org.koin.android.ext.android.inject

class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<PostDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        observeData()

        return binding.root
    }

    private fun observeData() {
        viewModel.getPostDetails(Common.postModel?.id.toString())
        viewModel.postDetailsResponse.observe(viewLifecycleOwner, {
            binding.tvDetailsTitle.text = it.name
            if (!it.isFromApi)
                Glide.with(requireContext()).load(Uri.parse(it.image)).into(binding.imgPost)
            else
                Glide.with(requireContext()).load(it.image).into(binding.imgPost)
        })

    }

}