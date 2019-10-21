package com.example.quizen.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class User {

    String userid;

    List<Quiz> quizs;

    public User(JSONObject job) throws JSONException {
        this.userid = job.getString("userid");

        if (job.has("quizs")) {
            JSONArray quizs = job.getJSONArray("quizs");
            for(int i=0; i < quizs.length(); i++) {
                this.quizs.add(new Quiz(quizs.getJSONObject(i)));
            }
        }

    }


}
