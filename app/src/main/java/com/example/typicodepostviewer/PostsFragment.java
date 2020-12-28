package com.example.typicodepostviewer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    private static final String TAG = "PostsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        rootView.setTag(TAG);

        // Initialize RecyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvPostList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // @TODO: Will this cause leaks?
        PostsAdapter adapter = new PostsAdapter(getActivity(), AppCacheManager.GetPosts());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}