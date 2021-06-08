package com.bryansoria.socialappv4.InterfazGame;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bryansoria.socialappv4.Game1;
import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;
import com.bryansoria.socialappv4.Ranking1;
import com.bryansoria.socialappv4.Ranking2;
import com.bryansoria.socialappv4.besdblackspiritgame.MainActivityDarkSpirit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DetalleJuego extends AppCompatActivity {

    private TextView tit, gen, descr;
    private ImageView portada;
    private ImageButton jugar, ranking;
    private Game game;



    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    private List juegos = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_juegos);

        //recuperarJuego();
        jugar=(ImageButton)findViewById(R.id.jugar) ;
        ranking=(ImageButton)findViewById(R.id.button2);
        tit=(TextView)findViewById(R.id.titulo);
        gen=(TextView)findViewById(R.id.ranking);
        descr=(TextView)findViewById(R.id.descripcion);
        portada =(ImageView)findViewById(R.id.portada);

        //Creamos objeto budle para recibir
        Bundle recibirBudle=getIntent().getExtras();
        game=null;
        //Validamos para verificar si exiten argumentos
        if(recibirBudle!=null){
            game=(Game)recibirBudle.getSerializable("a");
            //Estableemos los datos en las vistas
            tit.setText(game.getName());
            gen.setText(game.getGender());
            descr.setText(game.getDescription());
            portada.setImageResource(game.getImg());
        }

    }

    public void lanzarJuegoCliker(View view){
        if(jugar.isClickable()) {
            if (game.getImg() == R.drawable.clikergalleta) {
                guardarJuego(1);
                Intent i = new Intent(DetalleJuego.this, Game1.class);
                startActivity(i);
            }else if(game.getImg() == R.drawable.blackbird){
                guardarJuego(2);
                Intent i = new Intent(DetalleJuego.this, MainActivityDarkSpirit.class);
                startActivity(i);
            }
        }

    }


    public void lanzarRanking(View view) {
        if (ranking.isClickable()) {
            if (game.getId() == 1) {
                Intent i = new Intent(DetalleJuego.this, Ranking1.class);
                startActivity(i);
            }
            if (game.getId()==2){
                Intent h = new Intent(DetalleJuego.this, Ranking2.class);
                startActivity(h);
            }
        }
    }

    public void guardarJuego(int i){

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        if(i==1) {
            HashMap hashMap = new HashMap();
            hashMap.put("clicker", true);
            mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                }
            });
        }else if(i==2){
            HashMap hashMap = new HashMap();
            hashMap.put("darkspirit", true);
            mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                }
            });

        }

    }

}