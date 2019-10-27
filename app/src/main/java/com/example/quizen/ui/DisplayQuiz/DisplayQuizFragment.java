package com.example.quizen.ui.DisplayQuiz;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizen.R;
import com.example.quizen.data.Quiz;
import com.example.quizen.ui.gallery.GalleryViewModel;

import java.net.URI;
import java.util.List;

public class DisplayQuizFragment extends Fragment {

    private DisplayQuizViewModel mViewModel;
    GalleryViewModel galleryViewModel;
    Quiz quiz;
    TextView quizTitle;
    LinearLayout myLinearLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_displayquiz, container, false);
        quizTitle = root.findViewById(R.id.DisplayQuizTitle);
        myLinearLayout = root.findViewById(R.id.myLinearLayout);
        quizTitle.setText(quiz.getName());
        if (quiz.getQuestions() != null) {
            final TextView[] myTextViews = new TextView[quiz.getQuestions().size()]; // create an empty array;

            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                // create a new textview
                final LinearLayout layout = new LinearLayout(getContext());
                ImageView deleteImage = new ImageView(getContext());
                final TextView rowTextView = new TextView(getContext());


                deleteImage = (ImageView) getActivity().findViewById(R.id.DeleteQuiz);
                deleteImage.setMaxWidth(70 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                deleteImage.setMaxHeight(70 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));

                rowTextView.setText(quiz.getQuestions().get(i).getQuestion());
                rowTextView.setWidth(300 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                rowTextView.setHeight(35 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                rowTextView.setBackgroundColor(getResources().getColor(R.color.white));
                rowTextView.setTextColor(getResources().getColor(R.color.black));
                rowTextView.setTextSize(24);
                rowTextView.setTypeface(rowTextView.getTypeface(), Typeface.BOLD_ITALIC);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(16 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT),16 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT),16 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT),16 * ( getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                rowTextView.setLayoutParams(params);

                // add the textview to the linearlayout
                layout.addView(rowTextView);
                layout.addView(deleteImage);
                myLinearLayout.addView(layout);

                // save a reference to the textview for later
                myTextViews[i] = rowTextView;
            }
        }

        return root;
    }

    public void setSelectedQuiz(Quiz quiz) {
        this.quiz = quiz;

    }



}
