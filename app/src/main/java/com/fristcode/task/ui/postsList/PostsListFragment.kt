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
import androidx.recyclerview.widget.RecyclerView
import com.fristcode.task.R
import com.fristcode.task.adapter.PostAdapter
import com.fristcode.task.eventBus.EventDeletePost
import com.fristcode.task.eventBus.EventFragmentSelected
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_posts_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

class PostsListFragment : Fragment() {

    companion object {
        fun newInstance() = PostsListFragment()
    }

    private val viewModel by inject<PostsListViewModel>()

    var limit = 10
    var page = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts_list, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvPosts.isNestedScrollingEnabled = false

        viewModel.getPosts(limit.toString(), page.toString())
        viewModel.readAllData.observe(viewLifecycleOwner, Observer {

            val layoutManger = LinearLayoutManager(requireContext())
            rvPosts.layoutManager = layoutManger

            val data = it
            rvPosts.adapter = data?.let { it1 -> PostAdapter(it1, requireActivity()) }
        })

        pagination()

        fabAddPost.setOnClickListener {
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

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onDeletePost(event: EventDeletePost) {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage("Are sure to delete this post?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                viewModel.deletePost(event.postModel)
            }
            .setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
                dialog!!.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun pagination() {
        nested.setOnScrollChangeListener { v:NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){

                page++
                viewModel.getPosts(limit.toString(), page.toString())

            }
        }
    }
}