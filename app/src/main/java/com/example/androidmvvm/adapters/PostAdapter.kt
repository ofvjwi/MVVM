package com.example.androidmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvm.R
import com.example.androidmvvm.activity.MainActivity
import com.example.androidmvvm.models.Post
import com.example.androidmvvm.utils.Utils

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster_list, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]

        if (holder is PosterViewHolder) {

            val posterViewHolder = (holder as PosterViewHolder)

            val containerLayout = posterViewHolder.containerLayout
            val textViewTitle = posterViewHolder.textViewTitle
            val textViewBody = posterViewHolder.textViewBody

            textViewTitle.text = post.title.uppercase()
            textViewBody.text = post.body

            containerLayout.setOnLongClickListener {
                deletePostDialog(post)
                true
            }
        }
    }

    inner class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var containerLayout: LinearLayout = itemView.findViewById(R.id.item_container_layout)
        var textViewTitle: TextView = itemView.findViewById(R.id.tv_title)
        var textViewBody: TextView = itemView.findViewById(R.id.tv_body)

    }

    private fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete?"
        Utils.customDialog(activity, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
                activity.viewModel.apiPostDelete(post).observe(activity, Observer {
                    activity.viewModel.apiPostList()
                })
            }

            override fun onNegativeClick() {

            }
        })
    }
}


