package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView totalMarksTextView, percentageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalMarksTextView = findViewById(R.id.tvTotalMarks);
        percentageTextView = findViewById(R.id.tvPercentage);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);

        double percentage = totalQuestions > 0 ? (score / (double) (totalQuestions * 5)) * 100 : 0;

        totalMarksTextView.setText("Total Marks: " + score);
        percentageTextView.setText(String.format("Percentage: %.2f%%", percentage));
    }
}
