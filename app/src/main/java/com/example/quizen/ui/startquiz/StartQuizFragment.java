package com.example.quizen.ui.startquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.quizen.R;
import com.example.quizen.data.Question;
import com.example.quizen.data.Quiz;
import com.example.quizen.ui.DisplayQuiz.DisplayQuizFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StartQuizFragment extends Fragment {

    private Quiz quiz;
    private List<Question> finishedQuestions;
    private TextView quizTitle;
    private TextView question;
    private Question currentQuestion;
    private CheckBox answer1;
    private CheckBox answer2;
    private CheckBox answer3;
    private List<CheckBox> answerList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.start_quiz_fragment, container, false);
        quiz = DisplayQuizFragment.quiz;
        finishedQuestions = new ArrayList<>();
        // pass currentQuestion a random question from the quiz
        currentQuestion = quiz.getQuestions().get(new Random().nextInt(quiz.getQuestions().size()));
        quizTitle = root.findViewById(R.id.StartQuizTitle);
        quizTitle.setText(quiz.getName());
        question = root.findViewById(R.id.QuestionTitle);
        question.setText(currentQuestion.getQuestion());
        answer1 = root.findViewById(R.id.Answer1);
        answer2 = root.findViewById(R.id.Answer2);
        answer3 = root.findViewById(R.id.Answer3);
        answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        Collections.shuffle(answerList);
        answerList.get(0).setText(currentQuestion.getRightAnswer() + " ");
        answerList.get(1).setText(currentQuestion.getAnswer2() + " ");
        answerList.get(2).setText(currentQuestion.getAnswer3() + " ");
        Button submitButton = root.findViewById(R.id.SubmitButton);
        Button nextQuestionButton = root.findViewById(R.id.NextQuestionButton);
        submitButton.setOnClickListener(view ->{
            for (int i = 0; i < answerList.size(); i++) {
                if (answerList.get(i).getText().toString().equals(currentQuestion.getRightAnswer() + " ")) {
                    answerList.get(i).setBackgroundColor(getResources().getColor(R.color.green));
                }
                answerList.get(i).setClickable(false);
            }

        });
        nextQuestionButton.setOnClickListener(view -> {
            getNextQuestion(currentQuestion, root);
            for (int i = 0; i < answerList.size(); i++) {
                answerList.get(i).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        });


        return root;
    }

    /**
     * Displays next question (randomly)
     * @param currentQuestion Question currently displayed
     * @param root The layout inflated
     */
    private void getNextQuestion(Question currentQuestion, View root) {
        Question nextQuestion = quiz.getQuestions().get(new Random().nextInt(quiz.getQuestions().size()));
        finishedQuestions.add(currentQuestion);
        if (finishedQuestions.size() == quiz.getQuestions().size()) {
            DisplayQuizFragment displayQuizFragment = new DisplayQuizFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, displayQuizFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else while (finishedQuestions.contains(nextQuestion)) {
            nextQuestion = quiz.getQuestions().get(new Random().nextInt(quiz.getQuestions().size()));
        }
        quiz = DisplayQuizFragment.quiz;
        this.currentQuestion = nextQuestion;
        quizTitle.setText(quiz.getName());
        question.setText(nextQuestion.getQuestion());
        answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        // Randomizes the order of which the answers are displayed.
        Collections.shuffle(answerList);
        answerList.get(0).setClickable(true);
        answerList.get(1).setClickable(true);
        answerList.get(2).setClickable(true);
        answerList.get(0).setChecked(false);
        answerList.get(1).setChecked(false);
        answerList.get(2).setChecked(false);
        answerList.get(0).setText(nextQuestion.getRightAnswer() + " ");
        answerList.get(1).setText(nextQuestion.getAnswer2() + " ");
        answerList.get(2).setText(nextQuestion.getAnswer3() + " ");
    }
}
