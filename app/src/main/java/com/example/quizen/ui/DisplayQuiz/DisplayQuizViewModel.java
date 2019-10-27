package com.example.quizen.ui.DisplayQuiz;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizen.R;
import com.example.quizen.data.Quiz;
import com.example.quizen.ui.login.LoginFragment;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DisplayQuizViewModel extends AndroidViewModel {

    MutableLiveData<Quiz> selected;
    RequestQueue requestQueue;

    public DisplayQuizViewModel(Application context) {
        super(context);

    }


}
