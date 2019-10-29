package com.example.quizen.data;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Question {

    String question;
    String rightAnswer;
    String answer2;
    String answer3;


    public Question(JSONObject job) throws JSONException {

        this.question = job.getString("question");
        this.rightAnswer = job.getString("rightAnswer");
        this.answer2 = job.getString("answer2");
        this.answer3 = job.getString("answer3");

    }

    public Question(String question, String rightAnswer, String answer2, String answer3) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getQuestion() {
        return question;
    }
}
