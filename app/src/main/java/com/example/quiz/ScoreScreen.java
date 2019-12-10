package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ScoreScreen extends AppCompatActivity {
    TextView tvScore;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_score_screen);

        tvScore = findViewById(R.id.tvScoreFinal);

        back = findViewById(R.id.btnBack);

        Intent intentScoreAccess = getIntent();
        tvScore.setText("" + intentScoreAccess.getStringExtra("score"));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(ScoreScreen.this, MainActivity.class);
                startActivity(goHome);
            }
        });

    }
}
