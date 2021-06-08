package com.bryansoria.socialappv4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bryansoria.socialappv4.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView tvUser1;
    private TextView tvUser2;
    private TextView tvUser3;
    private TextView tvUser4;
    private TextView tvUser5;
    private TextView tvUser6;
    private TextView tvUser7;
    private TextView tvUser8;
    private TextView tvUser9;
    private TextView tvUser10;
    private TextView tvScore1, tvScore2, tvScore3, tvScore4, tvScore5, tvScore6, tvScore7, tvScore8,tvScore9,tvScore10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking2);

        tvUser1 = (TextView) findViewById(R.id.tvUser12);
        tvUser2 = (TextView) findViewById(R.id.tvUser22);
        tvUser3 = (TextView) findViewById(R.id.tvUser32);
        tvUser4 = (TextView) findViewById(R.id.tvUser42);
        tvUser5 = (TextView) findViewById(R.id.tvUser52);
        tvUser6 = (TextView) findViewById(R.id.tvUser62);
        tvUser7 = (TextView) findViewById(R.id.tvUser72);
        tvUser8 = (TextView) findViewById(R.id.tvUser82);
        tvUser9 = (TextView) findViewById(R.id.tvUser92);
        tvUser10 = (TextView) findViewById(R.id.tvUser102);

        tvScore1 = (TextView) findViewById(R.id.tvScore12);
        tvScore2 = (TextView) findViewById(R.id.tvScore22);
        tvScore3 = (TextView) findViewById(R.id.tvScore32);
        tvScore4 = (TextView) findViewById(R.id.tvScore42);
        tvScore5 = (TextView) findViewById(R.id.tvScore52);
        tvScore6 = (TextView) findViewById(R.id.tvScore62);
        tvScore7 = (TextView) findViewById(R.id.tvScore72);
        tvScore8 = (TextView) findViewById(R.id.tvScore82);
        tvScore9 = (TextView) findViewById(R.id.tvScore92);
        tvScore10 = (TextView) findViewById(R.id.tvScore102);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayList<Users> score = new ArrayList<>();
        mDatabase.child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                            score.add(childSnapshot.getValue(Users.class));

                        Collections.sort(score, new Comparator<Users>() {
                            @Override
                            public int compare(Users o1, Users o2) {
                                return new String(o2.getScore2best()).compareTo(new String(o1.getScore2best()));
                            }
                        });
                        int aux = (score.size() - 1);
                        if (aux >= 0) {
                            tvUser1.setText(score.get((aux)).getUsername());
                            tvScore1.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser1.setText("--------");
                            tvScore1.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser2.setText(score.get((aux)).getUsername());
                            tvScore2.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser2.setText("--------");
                            tvScore2.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser3.setText(score.get((aux)).getUsername());
                            tvScore3.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser3.setText("--------");
                            tvScore3.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser4.setText(score.get((aux)).getUsername());
                            tvScore4.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser4.setText("--------");
                            tvScore4.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser5.setText(score.get((aux)).getUsername());
                            tvScore5.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser5.setText("--------");
                            tvScore5.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser6.setText(score.get((aux)).getUsername());
                            tvScore6.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser6.setText("--------");
                            tvScore6.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser7.setText(score.get((aux)).getUsername());
                            tvScore7.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser7.setText("--------");
                            tvScore7.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser8.setText(score.get((aux)).getUsername());
                            tvScore8.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser8.setText("--------");
                            tvScore8.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser9.setText(score.get((aux)).getUsername());
                            tvScore9.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser9.setText("--------");
                            tvScore9.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser10.setText(score.get((aux)).getUsername());
                            tvScore10.setText(score.get((aux)).getScore2best());
                            aux--;
                        } else {
                            tvUser10.setText("--------");
                            tvScore10.setText("--------");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}