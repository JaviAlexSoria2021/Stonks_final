package com.bryansoria.socialappv4.InterfazGame;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bryansoria.socialappv4.Game1;
import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;
import com.bryansoria.socialappv4.Ranking1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class DetalleJuego extends AppCompatActivity {

    private TextView tit, gen, descr;
    private ImageView portada;
    private ImageButton jugar;
    private Button ranking;
    private Game game;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List juegos = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_juegos);

        //recuperarJuego();
        jugar=(ImageButton)findViewById(R.id.jugar) ;
        ranking=(Button)findViewById(R.id.button2);
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
                Intent i = new Intent(DetalleJuego.this, Game1.class);
                startActivity(i);
            }
        }
    }

    public void lanzarRanking(View view){
        if(ranking.isClickable()){
            Intent i = new Intent(DetalleJuego.this, Ranking1.class);
            startActivity(i);
        }
    }


}