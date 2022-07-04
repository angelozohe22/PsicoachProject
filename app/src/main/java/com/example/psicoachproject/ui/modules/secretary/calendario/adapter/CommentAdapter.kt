package com.example.psicoachproject.ui.modules.secretary.calendario.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ItemCommentBinding
import com.example.psicoachproject.domain.model.Comment

/**
 * Created by Angelo on 7/19/2021
 */
class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var _commentsList = mutableListOf<Comment>()

    fun setData(data: MutableList<Comment>){
        this._commentsList = data
        notifyDataSetChanged()
    }

    fun addComment(item: Comment){
        _commentsList.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = _commentsList[position]
        holder.bindView(comment)
    }

    override fun getItemCount(): Int = _commentsList.size

    inner class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val commentBinding = ItemCommentBinding.bind(itemView)

        fun bindView(comment: Comment){
            commentBinding.apply {
                itemTvMessageComment.text = comment.comment
            }
        }
    }

}