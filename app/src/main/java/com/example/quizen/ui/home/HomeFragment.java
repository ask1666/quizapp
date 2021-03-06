package com.example.quizen.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.quizen.MainActivity;
import com.example.quizen.R;
import com.example.quizen.ui.createquiz.CreateQuizFragment;
import com.example.quizen.ui.createuser.CreateUserFragment;
import com.example.quizen.ui.gallery.GalleryFragment;
import com.example.quizen.ui.login.LoginFragment;
import com.example.quizen.ui.myquizgallery.MyQuizGalleryFragment;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button loginBtn = root.findViewById(R.id.LoginBtn);
        Button displayQuizBtn = root.findViewById(R.id.DisplayQuizBtn);
        Button displayMyQuizesBtn = root.findViewById(R.id.DisplayMyQuizBtn);
        Button createQuizBtn = root.findViewById(R.id.CreateQuizBtn);
        Button createUserBtn = root.findViewById(R.id.CreateUserBtn);
        loginBtn.setOnClickListener(view -> {
            LoginFragment fragment = new LoginFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        });

        displayQuizBtn.setOnClickListener(view -> {
            GalleryFragment fragment = new GalleryFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        });

        displayMyQuizesBtn.setOnClickListener(view -> {
            MyQuizGalleryFragment fragment = new MyQuizGalleryFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        });

        createQuizBtn.setOnClickListener(view -> {
            if (!MainActivity.loggedInUser.equals("User")) {
                CreateQuizFragment fragment = new CreateQuizFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("You are not logged in!")
                        .setMessage("If you want to create a Quiz you need to log in first. Do you want to login now?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            LoginFragment fragment = new LoginFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentManager.popBackStack();
                            fragmentTransaction.commit();
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        createUserBtn.setOnClickListener(view -> {
            CreateUserFragment fragment = new CreateUserFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        });

        return root;
    }
}