package com.example.quizen.ui.myquizgallery;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizen.R;
import com.example.quizen.ui.gallery.GalleryViewModel;
import com.example.quizen.ui.gallery.QuizRecyclerViewAdapter;

public class MyQuizGalleryFragment extends Fragment {

    private static GalleryViewModel model;
    private static RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        model = ViewModelProviders.of(this.getActivity()).get(GalleryViewModel.class);

        model.getQuizList().observe(getViewLifecycleOwner(), quiz -> {
            recyclerView.setAdapter(new MyQuizRecyclerViewAdapter(quiz));

        });

        return recyclerView;
    }
}
