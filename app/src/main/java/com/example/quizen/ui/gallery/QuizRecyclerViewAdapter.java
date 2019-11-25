package com.example.quizen.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizen.MainActivity;
import com.example.quizen.R;
import com.example.quizen.data.Quiz;
import com.example.quizen.ui.DisplayQuiz.DisplayQuizFragment;
import com.example.quizen.ui.DisplayQuiz.DisplayQuizViewModel;
import com.example.quizen.ui.home.HomeFragment;
import com.example.quizen.ui.login.LoginFragment;


import java.util.List;

public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.ViewHolder> {

    private List<Quiz> mData;
    private GalleryViewModel model;
    private DisplayQuizFragment displayQuizFragment;


    public QuizRecyclerViewAdapter(List<Quiz> items) {
        mData = items;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);

        model = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(GalleryViewModel.class);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.quiz = mData.get(position);
        holder.quizTitle.setText(mData.get(position).getName());
        holder.image.setOnClickListener(view -> {
            if (MainActivity.loggedInUser.equals(holder.quiz.getUserid())) {
                model.deleteItem(holder.quizTitle.getText().toString());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.context);
                builder.setTitle("You are not logged in!");
                builder.setPositiveButton("Ok", (dialog, which) -> {
                    dialog.cancel();
                });

                builder.show();
            }
        });
        holder.quizTitle.setOnClickListener( view -> {
            displayQuizFragment = new DisplayQuizFragment();
            displayQuizFragment.quizTitlee = holder.quizTitle.getText().toString();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, displayQuizFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

    }



    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        final TextView quizTitle;
        final ImageView image;
        public Quiz quiz;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.DeleteQuiz);
            quizTitle = itemView.findViewById(R.id.Quiz);
            context = itemView.getContext();
        }
    }



}