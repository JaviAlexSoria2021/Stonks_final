package com.bryansoria.socialappv4.InterfazGame;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bryansoria.socialappv4.MainActivity;
import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList juegosJugados = new ArrayList();
    private ArrayList allGames = new ArrayList();
    private View decorView;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //Leemos el array con los juegos jugados o lo inicializamos
        juegosJugados = StorageConfig.leerListaJuego(getApplicationContext());
        if(juegosJugados==null)
            juegosJugados=new ArrayList();

    }


    public void mandarJuego(Game game){
        Intent i = new Intent(HomeActivity.this, DetalleJuego.class);
        i.putExtra("a", game);
        startActivity(i);
    }

}

