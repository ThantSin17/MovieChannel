package com.stone.moviechannel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.stone.moviechannel.data.Comment;
import com.stone.moviechannel.databinding.CommentItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context) {
        this.context = context;
        comments=new ArrayList<>();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CommentViewHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            Comment comment=comments.get(position);
            holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentItemBinding binding;
        public CommentViewHolder(@NonNull CommentItemBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
        public void bind(Comment comment){
            binding.userName.setText(comment.userName);
            binding.content.setText(comment.content);
            Glide.with(context).load(comment.photo).into(binding.profileImage);
        }
    }
}
