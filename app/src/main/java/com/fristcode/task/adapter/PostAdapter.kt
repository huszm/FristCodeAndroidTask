package com.fristcode.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fristcode.task.R
import com.fristcode.task.common.Common
import com.fristcode.task.eventBus.EventDeletePost
import com.fristcode.task.eventBus.EventFragmentSelected
import com.fristcode.task.model.PostModel
import org.greenrobot.eventbus.EventBus

class PostAdapter(var data: List<PostModel>, var activity: Context) :
    RecyclerView.Adapter<PostAdapter.ViewHolderIndex>() {

    class ViewHolderIndex(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(myData: PostModel, activity: Context) {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            val edit: ImageView = itemView.findViewById(R.id.edit)
            val delete: ImageView = itemView.findViewById(R.id.delete)


            title.text = myData.name

            delete.setOnClickListener {
                EventBus.getDefault().postSticky(EventDeletePost(myData))
            }
            edit.setOnClickListener {
                Common.postModel = myData
                EventBus.getDefault()
                    .postSticky(EventFragmentSelected(R.id.action_postsListFragment_to_editPostFragment))

            }
            itemView.setOnClickListener {
                Common.postModel = myData
                EventBus.getDefault()
                    .postSticky(EventFragmentSelected(R.id.action_postsListFragment_to_postDetailsFragment))
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderIndex {
        val myViewInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_post, parent, false)
        return ViewHolderIndex(myViewInflater)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolderIndex, position: Int) {
        val data = data[position]
        holder.bind(data, activity)
    }
}