package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bryansoria.socialappv4.ProfileActivity;
import com.bryansoria.socialappv4.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class GameOver extends AppCompatActivity {

    ImageButton mRestartButton;
    String scoreBestS,newScoreS;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef;
    DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        mRestartButton = findViewById(R.id.btnRestart);


        int scoreCount = getIntent().getExtras().getInt("score");
        SharedPreferences pref = getSharedPreferences("myStoragePreferences",0);
        int scoreBest = pref.getInt("scoreBest",0);
        SharedPreferences.Editor edit = pref.edit();
        if (scoreCount>scoreBest){
            scoreBest=scoreCount;
            edit.putInt("scoreBest",scoreBest);
            edit.commit();//guardamos el nuevo score
        }

        scoreBestS=String.valueOf(scoreBest);
        newScoreS=String.valueOf(scoreCount);
        saveScore2(scoreBestS,newScoreS);

        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(GameOver.this,MainActivityDarkSpirit.class);
                startActivity(myIntent);
            }
        });

    }




    public void saveScore2(String scoreBest, String score) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
            HashMap hashMap = new HashMap();
            hashMap.put("score2",score);
            hashMap.put("score2best", scoreBest);
            mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                }
            });
        }
}