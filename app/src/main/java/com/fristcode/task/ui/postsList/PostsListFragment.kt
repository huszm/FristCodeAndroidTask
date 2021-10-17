package com.fristcode.task.ui.postsList

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fristcode.task.R
import com.fristcode.task.adapter.PostAdapter
import com.fristcode.task.databinding.FragmentPostsListBinding
import com.fristcode.task.eventBus.EventDeletePost
import com.fristcode.task.eventBus.EventFragmentSelected
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

class PostsListFragment : Fragment() {

    private val viewModel by inject<PostsListViewModel>()
    private var _binding: FragmentPostsListBinding? = null
    private val binding get() = _binding!!

    var limit = 10
    var page = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvPosts.isNestedScrollingEnabled = false

        viewModel.getPosts(limit.toString(), page.toString())
        viewModel.readAllData.observe(viewLifecycleOwner, Observer {

            val layoutManger = LinearLayoutManager(requireContext())
            binding.rvPosts.layoutManager = layoutManger

            val data = it
            binding.rvPosts.adapter = data?.let { it1 -> PostAdapter(it1, requireActivity()) }
        })

        pagination()

        binding.fabAddPost.setOnClickListener {
            goToAddPostFragment()
        }
    }

    private fun goToAddPostFragment() {
        EventBus.getDefault()
            .postSticky(EventFragmentSelected(R.id.action_postsListFragment_to_addPostFragment))
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onDeletePost(event: EventDeletePost) {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage("Are sure to delete this post?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                viewModel.deletePost(event.postModel)
            }
            .setNegativeButton("No") { dialog: DialogInterface?, _: Int ->
                dialog!!.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun pagination() {
        binding.nested.setOnScrollChangeListener { v: NestedScrollView, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                viewModel.getPosts(limit.toString(), page.toString())
            }
        }
    }
}