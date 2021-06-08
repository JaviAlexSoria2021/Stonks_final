package com.bryansoria.socialappv4.InterfazGame;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.bryansoria.socialappv4.Game1;
import com.bryansoria.socialappv4.MainActivity;
import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;
import com.bryansoria.socialappv4.Ranking1;
import com.bryansoria.socialappv4.SetupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetalleJuego extends AppCompatActivity {

    private TextView tit, gen, descr;
    private ImageView portada;
    private ImageButton jugar;
    private Button ranking;
    private Game game;
    private Context context;
    public ArrayList <Game> juegosJugados = new ArrayList();

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    StorageReference StorageRef;
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

    public void guardarJuego(){

            mAuth=FirebaseAuth.getInstance();
            mUser=mAuth.getCurrentUser();
            mRef= FirebaseDatabase.getInstance().getReference().child("Users");
            HashMap hashMap = new HashMap();
            hashMap.put("clicker", true);
            mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                }
            });

    }

}