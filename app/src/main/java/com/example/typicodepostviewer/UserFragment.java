package com.example.typicodepostviewer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = requireArguments().getString("title");
        String body = requireArguments().getString("body");
        int userId = requireArguments().getInt("userId");

        User user = AppCacheManager.GetUser(userId);
        ((TextView) view.findViewById(R.id.tvUserPostTitle)).setText(title);
        ((TextView) view.findViewById(R.id.tvUserPostBody)).setText(body);
        ((TextView) view.findViewById(R.id.tvEmail)).setText(user.getEmail());
        ((TextView) view.findViewById(R.id.tvUserName)).setText(user.getName());
    }
}