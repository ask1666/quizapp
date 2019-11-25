package com.example.quizen.ui.createuser;

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
import com.example.quizen.R;
import com.example.quizen.ui.home.HomeFragment;
import com.example.quizen.ui.login.LoginFragment;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class CreateUserFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CreateUserViewModel createUserViewModel = ViewModelProviders.of(this).get(CreateUserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_createuser, container, false);

        Button createButton = root.findViewById(R.id.createuserBtn);
        Button cancelButton = root.findViewById(R.id.createuserCancelBtn);
        final EditText usernameInput = root.findViewById(R.id.createuserUsernameInput);
        final EditText passwordInput = root.findViewById(R.id.createuserPasswordInput);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(usernameInput, passwordInput);
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

    private void createUser(EditText usernameInput, EditText passwordInput) {
        final String username = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url ="http://15.188.88.253:8080/api/quizapp/createuser";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Error", error.toString());
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

        Fragment fragment = new LoginFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}