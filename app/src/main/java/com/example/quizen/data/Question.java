package com.example.quizen.data;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Question {

    String question;
    String answer;


    public Question(JSONObject job) throws JSONException {

        this.question = job.getString("question");
        this.answer = job.getString("answer");
    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
