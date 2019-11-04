package com.example.quizen.ui.DisplayQuiz;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizen.MainActivity;
import com.example.quizen.R;
import com.example.quizen.data.Question;
import com.example.quizen.data.Quiz;
import com.example.quizen.data.User;
import com.example.quizen.ui.gallery.GalleryViewModel;
import com.example.quizen.ui.login.LoginFragment;
import com.example.quizen.ui.startquiz.StartQuizFragment;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayQuizFragment extends Fragment {

    private DisplayQuizViewModel mViewModel;
    GalleryViewModel galleryViewModel;
    public static Quiz quiz;
    public static String quizTitlee;
    private List<Quiz> quizList;
    TextView quizTitle;
    LinearLayout myLinearLayout;
    Button addQuestionButton;
    Button startButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_displayquiz, container, false);
        galleryViewModel = ViewModelProviders.of(this.getActivity()).get(GalleryViewModel.class);
        quizList = galleryViewModel.getQuizList().getValue();
        System.out.println(quizList);
        if (quizList != null) {
            for (int i = 0; i < quizList.size(); i++) {
                if (quizList.get(i).getName().equals(quizTitlee)) {
                    quiz = quizList.get(i);
                    System.out.println("alksdløajksd  ===" + quiz);
                }
            }
        }
        quizTitle = root.findViewById(R.id.DisplayQuizTitle);
        myLinearLayout = root.findViewById(R.id.myLinearLayout);
        addQuestionButton = root.findViewById(R.id.CreateQuestionButton);
        startButton = root.findViewById(R.id.StartQuizButton);
        quizTitle.setText(quizTitlee);
        addQuestionButton.setOnClickListener(view -> {
            if (!MainActivity.loggedInUser.equals("User")) {
                if (!MainActivity.loggedInUser.equals(quiz.getUserid())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("You cannot modify quiz made by another user!");
                    builder.setPositiveButton("Log in", (dialog, which) -> {
                        LoginFragment loginFragment = new LoginFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment, loginFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentManager.popBackStack();
                        fragmentTransaction.commit();
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Add Question");

                    final LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    EditText theQuestion = new EditText(getContext());
                    EditText rightAnswer = new EditText(getContext());
                    EditText answer2 = new EditText(getContext());
                    EditText answer3 = new EditText(getContext());
                    theQuestion.setHint("Enter a question.");
                    theQuestion.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                    rightAnswer.setHint("Enter the correct answer.");
                    answer2.setHint("Enter a filler answer");
                    answer3.setHint("Enter another filler answer");
                    linearLayout.addView(theQuestion);
                    linearLayout.addView(rightAnswer);
                    linearLayout.addView(answer2);
                    linearLayout.addView(answer3);
                    builder.setView(linearLayout);


                    builder.setPositiveButton("OK", (dialog, which) -> {
                        Question question = new Question(theQuestion.getText().toString(), rightAnswer.getText().toString(),
                                answer2.getText().toString(), answer3.getText().toString());
                        addQuestion(question);
                        quiz.addQuestion(question);
                        System.out.println(quiz.getQuestions());
                        updateLayout();


                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                    builder.show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("You need to log in to do that!");
                builder.setPositiveButton("Log in", (dialog, which) -> {
                    LoginFragment loginFragment = new LoginFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, loginFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentManager.popBackStack();
                    fragmentTransaction.commit();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                   dialog.cancel();
                });
                builder.show();
            }
        });

        startButton.setOnClickListener(view -> {
            StartQuizFragment startQuizFragment = new StartQuizFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, startQuizFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        });

        updateLayout();



        return root;
    }

    //public void setSelectedQuiz(Quiz quiz) {
     //   this.quiz = quiz;

   // }

    public void updateLayout() {
        myLinearLayout.removeAllViews();

        if (quiz.getQuestions() != null) {
            final TextView[] myTextViews = new TextView[quiz.getQuestions().size()]; // create an empty array;
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                // create a new textview
                final LinearLayout layout = new LinearLayout(getContext());
                ImageView deleteImage = new ImageView(getContext());
                final TextView rowTextView = new TextView(getContext());


                deleteImage.setImageResource(android.R.drawable.ic_menu_delete);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams1.setMargins(10, 0, 0, 0);
                deleteImage.setLayoutParams(layoutParams1);
                deleteImage.setBackgroundColor(getResources().getColor(R.color.black));

                rowTextView.setText(quiz.getQuestions().get(i).getQuestion());
                rowTextView.setWidth(280 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                //rowTextView.setHeight(50 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                rowTextView.setBackgroundColor(getResources().getColor(R.color.black));
                rowTextView.setTextColor(getResources().getColor(R.color.white));
                rowTextView.setTextSize(24);
                rowTextView.setTypeface(rowTextView.getTypeface(), Typeface.BOLD_ITALIC);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.setMargins(0,0,0,0);
                rowTextView.setLayoutParams(layoutParams2);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(100, 50, 30, 0);

                deleteImage.setOnClickListener(view -> {
                    if (MainActivity.loggedInUser.equals(quiz.getUserid())) {
                        deleteQuestion(rowTextView.getText().toString());
                        myLinearLayout.removeView(layout);
                        for (int ii = 0; ii < quiz.getQuestions().size(); ii++) {
                            if (quiz.getQuestions().get(ii).getQuestion().equals(rowTextView.getText().toString()))
                                quiz.removeQuestion(quiz.getQuestions().get(ii));
                        }
                        System.out.println(quiz.getQuestions());
                    } else if (MainActivity.loggedInUser.equals("User")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("You are not logged in!");
                        builder.setPositiveButton("Log in", (dialog, which) -> {
                            LoginFragment loginFragment = new LoginFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, loginFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentManager.popBackStack();
                            fragmentTransaction.commit();
                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.cancel();
                        });
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("You cannot modify quiz made by another user!");
                        builder.setPositiveButton("Log in", (dialog, which) -> {
                            LoginFragment loginFragment = new LoginFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, loginFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentManager.popBackStack();
                            fragmentTransaction.commit();
                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.cancel();
                        });
                        builder.show();
                    }
                });

                layout.setOrientation(LinearLayout.HORIZONTAL);
                // add the textview to the linearlayout
                layout.addView(rowTextView);
                layout.addView(deleteImage);
                myLinearLayout.addView(layout, layoutParams);

                // save a reference to the textview for later
                myTextViews[i] = rowTextView;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void addQuestion(Question question) {
        String quiz1 = quiz.getName();
        new AsyncTask<Void, Void, String>() {
            HttpURLConnection c = null;
            @Override
            protected String doInBackground(Void... voids) {
                try

                {

                    URL url = new URL("http://158.38.101.126:8080/api/quizapp/createquestion?question=" + question.getQuestion() + "&rightAnswer=" + question.getRightAnswer() + "&answer2=" + question.getAnswer2() + "&answer3=" + question.getAnswer3() + "&quiz=" + quiz1);
                    c = (HttpURLConnection) url.openConnection();
                    c.setUseCaches(true);
                    c.setRequestMethod("GET");


                    if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        System.out.println(c.getResponseCode());
                        return "Success";
                    }
                    System.err.println(c.getResponseCode());
                    return "Error creating question " + c.getResponseMessage();


                } catch(Exception e){
                    System.err.println("Failed to call " + e);
                    return new IOException("Error creating question", e).toString();
                } finally{
                    if (c != null) c.disconnect();
                }

            }


        }.execute();
        GalleryViewModel.dataChanged = true;


    }

    @SuppressLint("StaticFieldLeak")
    public void deleteQuestion (String question) {
        new AsyncTask<Void, Void, String>() {
            HttpURLConnection c = null;
            @Override
            protected String doInBackground(Void... voids) {
                try

                {

                    URL url = new URL("http://158.38.101.126:8080/api/quizapp/deletequestion?question=" + question + "&quiz=" + quiz.getName());
                    c = (HttpURLConnection) url.openConnection();
                    c.setUseCaches(true);
                    c.setRequestMethod("DELETE");


                    if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        System.out.println(c.getResponseCode());
                        return "Success";
                    }
                    System.err.println(c.getResponseCode());
                    return "Error deleting question " + c.getResponseMessage();


                } catch(Exception e){
                    System.err.println("Failed to call " + e);
                    return new IOException("Error deleting question", e).toString();
                } finally{
                    if (c != null) c.disconnect();
                }

            }


        }.execute();
        GalleryViewModel.dataChanged = true;


    }





}
