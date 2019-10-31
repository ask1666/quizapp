package com.example.quizen.ui.createquiz;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizen.MainActivity;
import com.example.quizen.R;
import com.example.quizen.ui.createuser.CreateUserFragment;
import com.example.quizen.ui.gallery.GalleryViewModel;
import com.example.quizen.ui.home.HomeFragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class CreateQuizFragment extends Fragment {

    private CreateQuizViewModel createQuizViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createQuizViewModel = ViewModelProviders.of(this).get(CreateQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_createquiz, container, false);
        Button cancelButton = root.findViewById(R.id.CreateQuizCancelBtn);
        Button createButton = root.findViewById(R.id.CreateQuizButton);
        final EditText quizTitle = root.findViewById(R.id.CreateQuizInput);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createQuiz(quizTitle.getText().toString(), MainActivity.loggedInUser);
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
                fragmentTransaction.commit();
            }
        });


        return root;
    }

    @SuppressLint("StaticFieldLeak")
    public void createQuiz(final String quizTitle, String loggedInUser) {

        new AsyncTask<Void, Void, String>() {
            HttpURLConnection c = null;
            @Override
            protected String doInBackground(Void... voids) {
                try

                {

                    URL url = new URL("http://158.38.101.126:8080/api/quizapp/createquiz?name=" + quizTitle + "&username=" + loggedInUser);
                    c = (HttpURLConnection) url.openConnection();
                    c.setUseCaches(true);
                    c.setRequestMethod("GET");


                    if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        System.out.println(c.getResponseCode());
                        return "Success";
                    }
                    System.err.println(c.getResponseCode());
                    return "Error logging in " + c.getResponseMessage();


                } catch(Exception e){
                    System.err.println("Failed to call " + e);
                    return new IOException("Error logging in", e).toString();
                } finally{
                    if (c != null) c.disconnect();
                }

            }


        }.execute();
        GalleryViewModel.dataChanged = true;

    }

}
