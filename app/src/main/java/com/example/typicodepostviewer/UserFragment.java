package com.example.typicodepostviewer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private static final String TAG = "UserFragment";
    private int mPostPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = requireArguments().getString("title");
        String body = requireArguments().getString("body");
        int userId = requireArguments().getInt("userId");
        mPostPosition = requireArguments().getInt("postPosition");

        User user = AppCacheManager.GetUser(userId);
        ((TextView) view.findViewById(R.id.tvUserPostTitle)).setText(title);
        ((TextView) view.findViewById(R.id.tvUserPostBody)).setText(body);
        ((TextView) view.findViewById(R.id.tvEmail)).setText(user.getEmail());
        ((TextView) view.findViewById(R.id.tvUserName)).setText(user.getName());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            AppCacheManager.RemovePost(mPostPosition);
            getActivity().getSupportFragmentManager().popBackStack();
            Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}