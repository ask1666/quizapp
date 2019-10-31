package com.example.quizen.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Quiz {

    String name;

    String userid;

    List<Question> questions;

    public Quiz(JSONObject job) throws JSONException {

        this.userid = job.getString("userid");
        this.name = job.getString("name");
        this.questions = new ArrayList<>();

        if (job.has("questions")) {
            JSONArray questions = job.getJSONArray("questions");

            for (int i=0; i < questions.length(); i++) {


                this.questions.add(new Question(questions.getJSONObject(i)));

            }
            System.out.println(this.questions);
        }

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public Quiz(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

}