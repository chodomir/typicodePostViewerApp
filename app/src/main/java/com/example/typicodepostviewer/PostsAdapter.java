package com.example.typicodepostviewer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private final List<Post> mPosts;
    private static RecyclerViewClickListener sItemListener;

    // Custom ViewHolder for PostsAdapter
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTvTitle;
        private final TextView mTvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }

        @Override
        public void onClick(View v) {
            sItemListener.recyclerViewItemClicked(v, this.getLayoutPosition());
        }

        public TextView getTvTitle() {
            return mTvTitle;
        }
        public TextView getTvBody() {
            return mTvBody;
        }
    }

    public PostsAdapter(List<Post> posts, RecyclerViewClickListener itemListener) {
        mPosts = posts;
        sItemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.getTvTitle().setText(post.getTitle());
        holder.getTvBody().setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
