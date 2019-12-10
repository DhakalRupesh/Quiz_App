package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.model.DbHelper;
import com.example.quiz.model.Question;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class game extends AppCompatActivity {

    private TextView tvQuestion, tvQuesCount, tvScore;
    private Button btn1, btn2, btn3, btn4, btnNextQuestion;

    private LinearLayout optionContainer;

    private List<Question> questionList;

    private int questionCounter;
    private int totalQuestionCounter;
    private Question currentQues;

    private ColorStateList defaultTextColor;
    private int score = 0;
    private int count = 0;
    private boolean answeredQues;
    private String correctAns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //hiding the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
//        //making full screen
//        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        //hiding the navigation bar
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_game);

        tvQuestion = findViewById(R.id.question);
        tvQuesCount = findViewById(R.id.qCount);
        tvScore = findViewById(R.id.tvScore);

        optionContainer = findViewById(R.id.btnContainer);

        btn1 = findViewById(R.id.btnOp1);
        btn2 = findViewById(R.id.btnOp2);
        btn3 = findViewById(R.id.btnOp3);
        btn4 = findViewById(R.id.btnOp4);
        btnNextQuestion = findViewById(R.id.btnNext);

        defaultTextColor = btn1.getHintTextColors();

        DbHelper dbHelper = new DbHelper(this);
        questionList = dbHelper.retriveQuestions();
        totalQuestionCounter = questionList.size();  // getting the total number of question
        Collections.shuffle(questionList);

        displayNextQues();

        for(int i=0; i < 4; i++){
            optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCorrectAnswer((Button)v);
                    enableOptions(false);
                    btnNextQuestion.setVisibility(View.VISIBLE);
                }
            });
        }

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                animate(tvQuestion, 0);
                displayNextQues();
                enableOptions(true);
                btnNextQuestion.setVisibility(View.GONE);
            }
        });
    }

    private void displayNextQues() {
        btn1.setTextColor(defaultTextColor);
        btn2.setTextColor(defaultTextColor);
        btn3.setTextColor(defaultTextColor);
        btn4.setTextColor(defaultTextColor);

       if(questionCounter < totalQuestionCounter){  // checking if the question is available or not
           currentQues = questionList.get(questionCounter);

           tvQuestion.setText(currentQues.getQues());
           btn1.setText(currentQues.getOpt1());
           btn2.setText(currentQues.getOpt2());
           btn3.setText(currentQues.getOpt3());
           btn4.setText(currentQues.getOpt4());
           correctAns=currentQues.getCorrectAns();


           questionCounter++;
           tvQuesCount.setText(+ questionCounter + "/" + totalQuestionCounter);
           answeredQues = false;
       }else {
           finishGame();
       }
    }

    private void animate(final View v , final int value){
        v.animate().alpha(value).scaleY(value).scaleX(value).setDuration(400).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value == 0 && count < 4){
                    animate(optionContainer.getChildAt(count), 0);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value == 0){
                    animate(v, 1);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void finishGame() {
        finish();
    }

    private void checkCorrectAnswer(Button selectedAnswer){

        if(selectedAnswer.getText().toString().equals(correctAns)){
            Toast.makeText(this, "correct Answer", Toast.LENGTH_LONG).show();
            selectedAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#388e3c")));
            selectedAnswer.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            score++;
            tvScore.setText("" + score);
        }
        else{
            selectedAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d32f2f")));
            selectedAnswer.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            Toast.makeText(this, "wrong Answer", Toast.LENGTH_LONG).show();
        }

    }

    private void enableOptions(boolean enable){
        for(int i = 0; i < 4; i++){
            optionContainer.getChildAt(i).setEnabled(enable);
        }
    }

}
