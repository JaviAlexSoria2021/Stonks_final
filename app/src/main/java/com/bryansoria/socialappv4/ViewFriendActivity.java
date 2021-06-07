package com.bryansoria.socialappv4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.internal.tls.BasicTrustRootIndex;

public class ViewFriendActivity extends AppCompatActivity {

    DatabaseReference mUserRef,requestRef,friendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageUrl,username,city,country;

    CircleImageView profileImage;
    TextView Username,address;
    Button btnPerform,btnDecline;
    String CurrentState="nothing_happen";
    String profession;
    String userID;

    String myProfileImageUrl,myUsername,myCity,myCountry,myProfession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);
        userID = getIntent().getStringExtra("userKey");

        Toast.makeText(this, ""+userID, Toast.LENGTH_SHORT).show();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        requestRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        friendRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        btnPerform = findViewById(R.id.btnPerfom);
        btnDecline = findViewById(R.id.btnDecline);

        profileImage = findViewById(R.id.profileImage);
        Username = findViewById(R.id.username);
        address = findViewById(R.id.address);


        LoadUser();

        LoadMyProfile();

        btnPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAction(userID);
            }
        });

        CheckUserExistance(userID);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unfriend(userID);
            }
        });


    }

    private void Unfriend(String userID) {
        if (CurrentState.equals("friend"))
        {
            friendRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        friendRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ViewFriendActivity.this, "You are unfriend", Toast.LENGTH_SHORT).show();
                                    CurrentState = "nothing_happen";
                                    btnPerform.setText("Send friend request");
                                    btnDecline.setVisibility(View.GONE);
                                }
                            }
                        });
                    }

                }
            });
        }
        if (CurrentState.equals("he_sent_pending")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","decline");
            requestRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "You have decline friend", Toast.LENGTH_SHORT).show();
                        CurrentState="he_sent_decline";
                        btnPerform.setVisibility(View.GONE);
                        btnDecline.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void CheckUserExistance(String userID) {
        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CurrentState="friend";
                    btnPerform.setText("Send SMS");
                    btnDecline.setText("Unfriend");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        friendRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CurrentState="friend";
                    btnPerform.setText("Send SMS");
                    btnDecline.setText("Unfriend");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState = "I_sent_pending";
                        btnPerform.setText("Cancel friend request");
                        btnDecline.setVisibility(View.GONE);
                    }
                    if (snapshot.child("status").getValue().toString().equals("decline")){
                        CurrentState = "I_sent_decline";
                        btnPerform.setText("Cancel friend request");
                        btnDecline.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState = "he_sent_pending";
                        btnPerform.setText("Accept friend request");
                        btnDecline.setText("Decline friend");
                        btnDecline.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
                    if (CurrentState.equals("nothing_happen")){
                        CurrentState = "nothing_happen";
                        btnPerform.setText("Send friend request");
                        btnDecline.setVisibility(View.GONE);
                    }
    }

    private void PerformAction(String userID) {
        //Primer Estado en donde los usuarios no se han enviado ninguna petion el uno al otro
        if (CurrentState.equals("nothing_happen")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");
            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "You have sent friend request", Toast.LENGTH_SHORT).show();
                        btnDecline.setVisibility(View.GONE);
                        CurrentState = "I_sent_pending";
                        btnPerform.setText("Cancel friend request");
                    }else {
                        Toast.makeText(ViewFriendActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (CurrentState.equals("I_sent_pending") || CurrentState.equals("I_sent_decline")){
            requestRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "You have cancelled friend request", Toast.LENGTH_SHORT).show();
                        CurrentState="nothing_happen";
                        btnPerform.setText("Send friend request");
                        btnDecline.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ViewFriendActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (CurrentState.equals("he_sent_pending")){
            requestRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        final HashMap hashMap = new HashMap();
                        hashMap.put("status","friend");
                        hashMap.put("username",username);//ya se cargan en el metodo LoadUser
                        hashMap.put("profileImageUrl",profileImageUrl);//ya se cargan en el metodo LoadUser
                        hashMap.put("profession",profession);

                        final HashMap hashMap1 = new HashMap();
                        hashMap.put("status","friend");
                        hashMap.put("username",myUsername);
                        hashMap.put("profileImageUrl",myProfileImageUrl);
                        hashMap.put("profession",myProfession);


                        friendRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    friendRef.child(userID).child(mUser.getUid()).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            Toast.makeText(ViewFriendActivity.this, "You added friend", Toast.LENGTH_SHORT).show();
                                            CurrentState = "friend";
                                            btnPerform.setText("Send SMS");
                                            btnDecline.setText("UnFriend");
                                            btnDecline.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
        if (CurrentState.equals("friend")){
            Intent intent = new Intent(ViewFriendActivity.this,ChatActivity.class);
            intent.putExtra("OtherUserID",userID);
            startActivity(intent);
        }
    }

    //Metodo para cargar el usuario seleccionado en la opcion del menu  Find Friends
    private void LoadUser() {

        mUserRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    profileImageUrl = snapshot.child("profileImage").getValue().toString();
                    username = snapshot.child("username").getValue().toString();
                    city = snapshot.child("city").getValue().toString(); //REVISAR VIDEO 33 LINA DUPLICADA EN SU VIDEO
                    country = snapshot.child("country").getValue().toString();
                    profession = snapshot.child("profession").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImage);
                    Username.setText(username);
                    address.setText(city+", "+country);

                } else {
                    Toast.makeText(ViewFriendActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewFriendActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void LoadMyProfile() {

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    myProfileImageUrl = snapshot.child("profileImage").getValue().toString();
                    myUsername = snapshot.child("username").getValue().toString();
                    myCity = snapshot.child("city").getValue().toString(); //REVISAR VIDEO 33 LINA DUPLICADA EN SU VIDEO
                    myCountry = snapshot.child("country").getValue().toString();
                    myProfession = snapshot.child("profession").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImage);
                    Username.setText(username);
                    address.setText(city+", "+country);

                } else {
                    Toast.makeText(ViewFriendActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewFriendActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}