package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView questionText, marksText, timeText;
    private RadioGroup optionsGroup;
    private Button nextButton, prevButton, showAnswerButton, endExamButton;

    private String[] questions;
    private String[][] options;
    private String[] correctAnswers;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 600000;
    private boolean[] isQuestionAnswered; // Array to track if each question is answered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.tvQuestion);
        marksText = findViewById(R.id.tvMarks);
        timeText = findViewById(R.id.tvTime);
        optionsGroup = findViewById(R.id.radioGroupOptions);
        showAnswerButton = findViewById(R.id.btnShowAnswer);
        nextButton = findViewById(R.id.btnNext);
        prevButton = findViewById(R.id.btnPrev);
        endExamButton = findViewById(R.id.btnEndExam);

        questions = getResources().getStringArray(R.array.questions);
        correctAnswers = getResources().getStringArray(R.array.correct_answers);
        options = new String[][]{
                getResources().getStringArray(R.array.options_q1),
                getResources().getStringArray(R.array.options_q2),
                getResources().getStringArray(R.array.options_q3),
                getResources().getStringArray(R.array.options_q4),
                getResources().getStringArray(R.array.options_q5),
                getResources().getStringArray(R.array.options_q6),
                getResources().getStringArray(R.array.options_q7),
                getResources().getStringArray(R.array.options_q8),
                getResources().getStringArray(R.array.options_q9),
                getResources().getStringArray(R.array.options_q10),
                getResources().getStringArray(R.array.options_q11),
                getResources().getStringArray(R.array.options_q12),
                getResources().getStringArray(R.array.options_q13),
                getResources().getStringArray(R.array.options_q14),
                getResources().getStringArray(R.array.options_q15),
                getResources().getStringArray(R.array.options_q16),
                getResources().getStringArray(R.array.options_q17),
                getResources().getStringArray(R.array.options_q18),
                getResources().getStringArray(R.array.options_q19),
                getResources().getStringArray(R.array.options_q20),
                getResources().getStringArray(R.array.options_q21),
                getResources().getStringArray(R.array.options_q22),
                getResources().getStringArray(R.array.options_q23),
                getResources().getStringArray(R.array.options_q24),
                getResources().getStringArray(R.array.options_q25)
        };
        isQuestionAnswered = new boolean[questions.length];

        displayQuestion(currentQuestionIndex);
        startTimer();

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!isQuestionAnswered[currentQuestionIndex]){
                  isQuestionAnswered[currentQuestionIndex] = true;
                  Toast.makeText(MainActivity.this, "Wrong! The correct answer is: " + correctAnswers[currentQuestionIndex], Toast.LENGTH_LONG).show();
                  score -= 1;
                  updateMarks();
              }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isQuestionAnswered[currentQuestionIndex]) {
                    isQuestionAnswered[currentQuestionIndex] = true;
                    checkAnswer();
                }

                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {
                    Toast.makeText(MainActivity.this, "You are done with the exam. GOOD LUCK!", Toast.LENGTH_LONG).show();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion(currentQuestionIndex);
                }
            }
        });

        endExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinalScore();
            }
        });
    }

    private void displayQuestion(int index) {
        questionText.setText(questions[index]);
        String[] currentOptions = options[index];
        ((RadioButton) findViewById(R.id.rbOption1)).setText(currentOptions[0]);
        ((RadioButton) findViewById(R.id.rbOption2)).setText(currentOptions[1]);
        ((RadioButton) findViewById(R.id.rbOption3)).setText(currentOptions[2]);
        ((RadioButton) findViewById(R.id.rbOption4)).setText(currentOptions[3]);
        optionsGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);

        if (selectedRadioButton != null) {
            String selectedOption = selectedRadioButton.getText().toString();
            if (selectedOption.equals(correctAnswers[currentQuestionIndex])) {
                score += 5;
                Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                score -= 1;
                Toast.makeText(MainActivity.this, "Wrong! The correct answer is: " + correctAnswers[currentQuestionIndex], Toast.LENGTH_SHORT).show();
            }
            updateMarks();
        } else {
            Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMarks() {
        marksText.setText("Marks: " + score);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                showFinalScore();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timeText.setText("Time Left: " + timeFormatted);
    }

    private void showFinalScore() {
        countDownTimer.cancel();
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questions.length);
        startActivity(intent);
    }
}
