package com.deborger.truecitizen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.deborger.truecitizen.databinding.ActivityMainBinding;
import com.deborger.truecitizen.model.Question;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private int currentQuestionIndex = 0;
    private final String TAG = "MainActivity";

    private Question[] mQuestions = new Question[] {
            // create questions objects
            new Question(R.string.question_amendments, false),
            new Question(R.string.question_constitution, true),
            new Question(R.string.question_declaration, true),
            new Question(R.string.question_independence_rights, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_government, false),
            new Question(R.string.question_government_feds, false),
            new Question(R.string.question_government_senators, true)
    };

    private int maxQuestions = mQuestions.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.questionTextView.setText(mQuestions[currentQuestionIndex].getAnswerResId());
        mBinding.trueButton.setOnClickListener(v -> checkAnswer(true));
        mBinding.falseButton.setOnClickListener(v -> checkAnswer(false));

        mBinding.nextButton.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: Click Next " + currentQuestionIndex);
            currentQuestionIndex = (currentQuestionIndex + 1) % maxQuestions; // incrementing
            updateQuestion();
        });

        mBinding.prevButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex = (currentQuestionIndex - 1) % maxQuestions; // decrementing
                updateQuestion();
            }
        });
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answerIsCorrect = mQuestions[currentQuestionIndex].isAnswerTrue();
        int messageId;

        if (answerIsCorrect == userChoseCorrect) {
            messageId = R.string.correct_answer;
        } else {
            messageId = R.string.wrong_answer;
        }
        Snackbar.make(mBinding.imageView, messageId, Snackbar.LENGTH_SHORT).show();
    }
    private void updateQuestion() {
        mBinding.questionTextView.setText(mQuestions[currentQuestionIndex].getAnswerResId());
    }

}