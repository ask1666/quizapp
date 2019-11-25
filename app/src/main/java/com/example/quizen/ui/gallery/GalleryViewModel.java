package com.example.quizen.ui.gallery;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizen.MainActivity;
import com.example.quizen.data.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

public class GalleryViewModel extends AndroidViewModel {

    private MutableLiveData<List<Quiz>> quizList = new MutableLiveData<>();
    private MutableLiveData<Quiz> selected = new MutableLiveData<>();
    public static Boolean  dataChanged = false;

    private RequestQueue requestQueue;

    public GalleryViewModel(Application context) {
        super(context);
        requestQueue = Volley.newRequestQueue(context);
    }

    public LiveData<List<Quiz>> getQuizList() {

        loadQuiz();
        System.out.println("GalleryViewModel = " + quizList.getValue());


        return quizList;
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteItem(String quizTitle) {

        new AsyncTask<Void, Void, String>() {
            HttpURLConnection c = null;
            @Override
            protected String doInBackground(Void... voids) {
                try

                {

                    URL url = new URL("http://15.188.88.253:8080/api/quizapp/deletequiz?name=" + quizTitle);
                    c = (HttpURLConnection) url.openConnection();
                    c.setUseCaches(true);
                    c.setRequestMethod("DELETE");


                    if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        System.out.println(c.getResponseCode());
                        return "Success";
                    }
                    System.err.println(c.getResponseCode());
                    return "Error deleting quiz " + c.getResponseMessage();


                } catch(Exception e){
                    System.err.println("Failed to call " + e);
                    return new IOException("Error deleting quiz", e).toString();
                } finally{
                    if (c != null) c.disconnect();
                }

            }


        }.execute();

        loadQuiz();
        dataChanged = true;
    }

    public LiveData<Quiz> getSelected() {
        return selected;
    }


    public void setSelected(Quiz selected) {
        this.selected.setValue(selected);
    }

    private void loadQuiz() {

        String url = "http://15.188.88.253:8080/api/quizapp/getallquiz";
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    List<Quiz> Quizes = new ArrayList<>();

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            Quizes.add(new Quiz(response.getJSONObject(i)));
                        }
                    } catch (JSONException jex) {
                        System.out.println(jex);
                    }
                    this.quizList.setValue(Quizes);
                }, System.out::println);
        requestQueue.add(jar);
        dataChanged = false;
    }
}