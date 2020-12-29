package com.example.typicodepostviewer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment implements RecyclerViewClickListener{
    public static final String TAG = "PostsFragment";

    private RecyclerView mRecyclerView;
    private List<Post> mPosts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void recyclerViewItemClicked(View view, int position) {
        TextView title = view.findViewById(R.id.tvTitle);
        TextView body = view.findViewById(R.id.tvBody);
        Bundle bundle = new Bundle();
        bundle.putInt("postPosition", position);
        bundle.putInt("userId", mPosts.get(position).getUserId());
        bundle.putString("title", title.getText().toString());
        bundle.putString("body", body.getText().toString());

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, UserFragment.class, bundle)
                .addToBackStack(null)
                .commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        rootView.setTag(TAG);

        // Initialize RecyclerView
        mRecyclerView = rootView.findViewById(R.id.rvPostList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPosts = MainActivity.sPostsCache.getData(MainActivity.POST_API_URL,
                false);
        PostsAdapter adapter = new PostsAdapter(mPosts, this);
        mRecyclerView.setAdapter(adapter);

        // menu
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.posts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mPosts = MainActivity.sPostsCache.getData(MainActivity.POST_API_URL,
                    true);
            PostsAdapter adapter = new PostsAdapter(mPosts, this);
            mRecyclerView.setAdapter(adapter);

            Toast.makeText(getContext(), "Syncing", Toast.LENGTH_LONG).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}