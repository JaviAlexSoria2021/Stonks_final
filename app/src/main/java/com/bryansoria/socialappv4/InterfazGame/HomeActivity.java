package com.bryansoria.socialappv4.InterfazGame;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bryansoria.socialappv4.Game1;
import com.bryansoria.socialappv4.MainActivity;
import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;
import com.bryansoria.socialappv4.besdblackspiritgame.MainActivityDarkSpirit;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mUserRef;
    FirebaseUser mUser;
    boolean comp , comp2;
    ImageView clicker, darkspirit;

    private ArrayList <Game> allGames = new <Game>ArrayList();
    private Context context;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        clicker=(ImageView)findViewById(R.id.imageGameJugado);
        darkspirit =(ImageView)findViewById(R.id.imageGameJugado2);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");


        //Inicializamos el action Bar
         BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.home));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.biblioteca:
                        startActivity(new Intent(getApplicationContext()
                                        , Biblioteca.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.post:
                        startActivity(new Intent(getApplicationContext()
                                ,
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        //Cargamos todos los juegos en el arrayMain
        cargarJuegos.cargar(allGames);

        //Ahora leemos los datos
        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    comp = (boolean)snapshot.child("clicker").getValue();
                        pintarJuegoUno(comp);
                    comp2 = (boolean)snapshot.child("darkspirit").getValue();
                        pintarJuegoDos(comp2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void lanzarJuegoClikerHome(View view){
        if(clicker.isClickable()) {
                Intent i = new Intent(HomeActivity.this, Game1.class);
                startActivity(i);
            }
    }

    public void lanzarJuegoDarkSpiritHome(View view){
        if(darkspirit.isClickable()) {
            Intent i = new Intent(HomeActivity.this, MainActivityDarkSpirit.class);
            startActivity(i);
        }
    }

    public void pintarJuegoUno(boolean x){
        if(x){
            clicker.setImageResource(R.drawable.clikergalleta);
        }
    }
    public void pintarJuegoDos(boolean x){
        if(x){
            darkspirit.setImageResource(R.drawable.blackbird);
        }
    }

}

