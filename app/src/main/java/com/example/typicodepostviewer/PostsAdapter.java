package com.example.typicodepostviewer;

import android.content.Context;
import android.os.Bundle;
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
    private final Context mContext;
    private final List<Post> mPosts;

    // Custom ViewHolder for PostsAdapter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvTitle;
        private final TextView mTvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }

        public TextView getTvTitle() {
            return mTvTitle;
        }
        public TextView getTvBody() {
            return mTvBody;
        }
    }

    public PostsAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = new ArrayList<>(posts);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        // @TODO: Solve the "setTag(id)" problem
        view.setOnClickListener(v -> {
            TextView title = (TextView) v.findViewById(R.id.tvTitle);
            TextView body = (TextView) v.findViewById(R.id.tvBody);
            Bundle bundle = new Bundle();
            bundle.putInt("userId", (int) title.getTag());
            bundle.putString("title", title.getText().toString());
            bundle.putString("body", body.getText().toString());

            ((MainActivity)mContext).getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view, UserFragment.class, bundle)
                    .addToBackStack(null)
                    .commit();
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.getTvTitle().setText(post.getTitle());
        holder.getTvBody().setText(post.getBody());
        // @TODO: hacky way of doing it, not sure how to do it otherwise
        holder.getTvTitle().setTag(post.getUserId());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
