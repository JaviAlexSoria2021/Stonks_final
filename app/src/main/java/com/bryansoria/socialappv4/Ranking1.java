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

public class Ranking1 extends AppCompatActivity {

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
        setContentView(R.layout.activity_ranking1);

        tvUser1 = (TextView)findViewById(R.id.tvUser1);
        tvUser2 = (TextView)findViewById(R.id.tvUser2);
        tvUser3 = (TextView)findViewById(R.id.tvUser3);
        tvUser4 = (TextView)findViewById(R.id.tvUser4);
        tvUser5 = (TextView)findViewById(R.id.tvUser5);
        tvUser6 = (TextView)findViewById(R.id.tvUser6);
        tvUser7 = (TextView)findViewById(R.id.tvUser7);
        tvUser8 = (TextView)findViewById(R.id.tvUser8);
        tvUser9 = (TextView)findViewById(R.id.tvUser9);
        tvUser10 = (TextView)findViewById(R.id.tvUser10);

        tvScore1 = (TextView)findViewById(R.id.tvScore1);
        tvScore2 = (TextView)findViewById(R.id.tvScore2);
        tvScore3 = (TextView)findViewById(R.id.tvScore3);
        tvScore4 = (TextView)findViewById(R.id.tvScore4);
        tvScore5 = (TextView)findViewById(R.id.tvScore5);
        tvScore6 = (TextView)findViewById(R.id.tvScore6);
        tvScore7 = (TextView)findViewById(R.id.tvScore7);
        tvScore8 = (TextView)findViewById(R.id.tvScore8);
        tvScore9 = (TextView)findViewById(R.id.tvScore9);
        tvScore10 = (TextView)findViewById(R.id.tvScore10);




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
                                return new String(o1.getScore1()).compareTo(new String(o2.getScore1()));
                            }
                        });
                        int aux = (score.size()-1);
                        if (aux >= 0) {
                            tvUser1.setText(score.get((aux)).getUsername());
                            tvScore1.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser1.setText("--------");
                            tvScore1.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser2.setText(score.get((aux)).getUsername());
                            tvScore2.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser2.setText("--------");
                            tvScore2.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser3.setText(score.get((aux)).getUsername());
                            tvScore3.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser3.setText("--------");
                            tvScore3.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser4.setText(score.get((aux)).getUsername());
                            tvScore4.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser4.setText("--------");
                            tvScore4.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser5.setText(score.get((aux)).getUsername());
                            tvScore5.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser5.setText("--------");
                            tvScore5.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser6.setText(score.get((aux)).getUsername());
                            tvScore6.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser6.setText("--------");
                            tvScore6.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser7.setText(score.get((aux)).getUsername());
                            tvScore7.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser7.setText("--------");
                            tvScore7.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser8.setText(score.get((aux)).getUsername());
                            tvScore8.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser8.setText("--------");
                            tvScore8.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser9.setText(score.get((aux)).getUsername());
                            tvScore9.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser9.setText("--------");
                            tvScore9.setText("--------");
                        }
                        if (aux >= 0) {
                            tvUser10.setText(score.get((aux)).getUsername());
                            tvScore10.setText(score.get((aux)).getScore1());
                            aux--;
                        }
                        else
                        {
                            tvUser10.setText("--------");
                            tvScore10.setText("--------");
                        }

                        /*int j=score.size();
                        for(int i=0; i<score.size(); i++)
                        {
                            users[i]=score.get(j).getName();
                            j--;
                        }*/

                        //tvRanking.setText(score.get(1).getScore1());


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        /*ArrayAdapter<String> usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        lvUsers.setAdapter(usersAdapter);*/
    }
    /*private void setRanking()
    {
        ArrayList<User> score = new ArrayList<>();
        mDatabase.child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                            score.add(childSnapshot.getValue(User.class));

                        Collections.sort(score, new Comparator<User>() {
                            @Override
                            public int compare(User o1, User o2) {
                                return new String(o1.getScore1()).compareTo(new String(o2.getScore1()));
                            }
                        });
                        int j=0;
                        for(int i=9; i>=0; i--)
                        {
                            users[i]=score.get(j).getName();
                            j++;
                        }

                        //tvRanking.setText(score.get(1).getScore1());


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }*/
}