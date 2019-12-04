package com.example.quizen.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quizen.MainActivity;
import com.example.quizen.R;
import com.example.quizen.data.Quiz;
import com.example.quizen.ui.DisplayQuiz.DisplayQuizFragment;
import com.example.quizen.ui.home.HomeFragment;
import com.example.quizen.ui.login.LoginFragment;

import java.util.List;

public class GalleryFragment extends Fragment {

    private static RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GalleryViewModel model = ViewModelProviders.of(this.getActivity()).get(GalleryViewModel.class);
        // Display list of quiz from database in recycler view.
        model.getQuizList().observe(getViewLifecycleOwner(), quiz -> {
            recyclerView.setAdapter(new QuizRecyclerViewAdapter(quiz));

        });

        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated((RecyclerView) view, savedInstanceState);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}