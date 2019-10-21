package com.example.quizen.ui.createquestion;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizen.R;
import com.example.quizen.ui.home.HomeFragment;

public class CreateQuestionFragment extends Fragment {

    private CreateQuestionViewModel createQuestionViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createQuestionViewModel = ViewModelProviders.of(this).get(CreateQuestionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_createquestion, container, false);

        Button addQuestionButton = root.findViewById(R.id.AddQuestionButton);
        Button cancelButton = root.findViewById(R.id.CreateQuestionCancelBtn);
        EditText questionInput = root.findViewById(R.id.CreateQuestionInput);
        EditText rightAnswerInput = root.findViewById(R.id.CreateRightQuestionAnswerInput);
        EditText answer2 = root.findViewById(R.id.CreateQuestionAnswerInput2);
        EditText answer3 = root.findViewById(R.id.CreateQuestionAnswerInput3);

        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createQuestion();
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    public void createQuestion() {

    }

}
