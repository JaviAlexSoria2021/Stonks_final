package com.bryansoria.socialappv4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

public class ViewFriendActivity extends AppCompatActivity {

    DatabaseReference mUserRef,requestRef,friendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageUrl,username,city,country;

    CircleImageView profileImage;
    TextView Username,address;
    Button btnPerform,btnDecline;
    String CurrentState="no_event";
    String profession;
    String userID;

    String myProfileImageUrl,myUsername,myCity,myCountry,myProfession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userID = getIntent().getStringExtra("userKey");

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

        //Boton de enviar solicitud de amistad
        btnPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions(userID);
            }
        });

        CheckUser(userID);

        //Boton de rechazar solicitud de amistad
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unfriend(userID);
            }
        });


    }

    //metodo para eliminar un amigo
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
                                    Toast.makeText(ViewFriendActivity.this, "Se ha eliminado de amigos", Toast.LENGTH_SHORT).show();
                                    CurrentState = "no_event";
                                    btnPerform.setText("Enviar solicitud de amistad.");
                                    btnDecline.setVisibility(View.GONE);
                                }
                            }
                        });
                    }

                }
            });
        }
        //Este metodo comprueba si los usuarios son amigos
        if (CurrentState.equals("he_sent_pending")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","decline");
            requestRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "Has rechazado la solicitud de amistad", Toast.LENGTH_SHORT).show();
                        CurrentState="he_sent_decline";
                        btnPerform.setVisibility(View.GONE);
                        btnDecline.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void CheckUser(String userID) {
        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CurrentState="friend";
                    btnPerform.setText("Enviar Mensaje");
                    btnDecline.setText("Eliminar amigo");
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
                    btnPerform.setText("Enviar Mensaje");
                    btnDecline.setText("Eliminar amigo");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //dependiendo de las condiciones tendremos que cambiar los estados de los usuarios
        requestRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState = "I_sent_pending";
                        btnPerform.setText("Cancelar solicitud de amistad");
                        btnDecline.setVisibility(View.GONE);
                    }
                    if (snapshot.child("status").getValue().toString().equals("decline")){
                        CurrentState = "I_sent_decline";
                        btnPerform.setText("Cancelar solicitud de amistad");
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
                        btnPerform.setText("Aceptar solicitud de amistad");
                        btnDecline.setText("Rechazar amigo");
                        btnDecline.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
                    if (CurrentState.equals("no_event")){
                        CurrentState = "no_event";
                        btnPerform.setText("Enviar solicitud de amistad.");
                        btnDecline.setVisibility(View.GONE);
                    }
    }

    private void Actions(String userID) {
        //Primer Estado en donde los usuarios no se han enviado ninguna petion el uno al otro
        //es decir, aun no son amigos
        if (CurrentState.equals("no_event")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");
            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "Se ha enviado una petición de amistad", Toast.LENGTH_SHORT).show();
                        btnDecline.setVisibility(View.GONE);
                        CurrentState = "I_sent_pending";
                        btnPerform.setText("Cancelar solicitud de amistad.");
                    }else {
                        Toast.makeText(ViewFriendActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //Si se ha enviado una petion de amistad el usuario solo podra tener la opcion de cancelar la solicitud
        if (CurrentState.equals("I_sent_pending") || CurrentState.equals("I_sent_decline")){
            requestRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "Has cancelado la solicitud de amistad", Toast.LENGTH_SHORT).show();
                        CurrentState="no_event";//volvemos al estado inicial
                        btnPerform.setText("Enviar solicitud de amistad.");//Por lo que el usuario puede volver a enviar una peticion de amistad
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
                        hashMap.put("username",username);
                        hashMap.put("profileImageUrl",profileImageUrl);
                        hashMap.put("profession",profession);//Cuando aceptamos la peticion de amistad necesitamos guardar la informacion del otro usuario

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
                                            Toast.makeText(ViewFriendActivity.this, "Se ha añadido como amigo", Toast.LENGTH_SHORT).show();
                                            CurrentState = "friend";
                                            //Al ser amigos se desbloquean las opciones de enviar mensaje y eliminar amigo
                                            btnPerform.setText("Enviar Mensaje");
                                            btnDecline.setText("Eliminar amigo");
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
        //Si ya se es "amigo" se podra acceder  al chat
        if (CurrentState.equals("friend")){
            Intent intent = new Intent(ViewFriendActivity.this,ChatActivity.class);
            intent.putExtra("OtherUserID",userID);
            startActivity(intent);
        }
    }

    //Metodo para cargar el usuario seleccionado del menu de Usuarios
    private void LoadUser() {

        mUserRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    profileImageUrl = snapshot.child("profileImage").getValue().toString();
                    username = snapshot.child("username").getValue().toString();
                    city = snapshot.child("city").getValue().toString();
                    country = snapshot.child("country").getValue().toString();
                    profession = snapshot.child("profession").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImage);
                    Username.setText(username);
                    address.setText(city+", "+country);

                } else {
                    Toast.makeText(ViewFriendActivity.this, "No se han encontrado datos", Toast.LENGTH_SHORT).show();
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