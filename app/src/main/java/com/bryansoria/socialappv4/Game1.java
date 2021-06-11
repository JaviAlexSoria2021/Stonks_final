package com.bryansoria.socialappv4;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Game1 extends AppCompatActivity {

    TextView time;
    TextView click;
    TextView tvScore;
    ImageButton btnCookie;
    Button btnStart;

    CountDownTimer timer;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    int t=10;
    int c=0;
    int score =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        time = (TextView) findViewById(R.id.time);
        click = (TextView) findViewById(R.id.click);
        tvScore = (TextView) findViewById(R.id.tvScore);
        btnCookie = (ImageButton)findViewById(R.id.btnCookie);
        btnStart = (Button)findViewById(R.id.btnStart);

        btnStart.setEnabled(true);
        btnCookie.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                t--;
                time.setText("Time : " + t);

            }

            @Override
            public void onFinish() {
                btnStart.setEnabled(true);
                btnCookie.setEnabled(false);
                time.setText("Time : 0");
                score = c;
                setScore();

            }
        };

        btnCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                tvScore.setText(""+c);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                btnCookie.setEnabled(true);
                btnStart.setEnabled(false);
                t=10;
                time.setText("Time : " + t);
                tvScore.setText("0");
            }
        });
    }

    private void setScore() {
        String id = mAuth.getCurrentUser().getUid();
        int score = Integer.valueOf(tvScore.getText().toString());
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String score_aux_str = snapshot.child("score1").getValue().toString();

                    int score_aux = Integer.valueOf(score_aux_str);

                    if (score_aux < score)
                    {
                        String new_score = ""+score;
                        Map<String, Object> scoreMap = new HashMap<>();
                        scoreMap.put("score1", new_score);
                        mDatabase.child("Users").child(id).updateChildren(scoreMap);
                    }

                }
                else
                {
                    Toast.makeText(Game1.this, "ERROR", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


    }
}