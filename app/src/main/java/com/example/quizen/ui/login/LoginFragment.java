package com.example.quizen.ui.login;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
import com.example.quizen.ui.home.HomeFragment;
import com.squareup.picasso.RequestHandler;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText usernameInput;
    private EditText passwordInput;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        Button loginButton = root.findViewById(R.id.loginBtn);
        Button createUserButton = root.findViewById(R.id.loginCreateuserBtn);

        usernameInput = (EditText) root.findViewById(R.id.loginUsernameInput);
        passwordInput = (EditText) root.findViewById(R.id.loginPasswordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            login(usernameInput, passwordInput);

            }
        });

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CreateUserFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    public void login(EditText usernameInput, EditText passwordInput) {
        final String username = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url ="http://15.188.88.253:8080/api/quizapp/login";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                        MainActivity.loggedInUser = usernameInput.getText().toString();
                        MainActivity.setToolbarText();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Error", error.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("No User with that Password!");
                builder.setPositiveButton("OK", (dialog,which) -> {
                    dialog.cancel();
                });
                builder.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", username);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);



    }
}