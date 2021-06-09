package com.bryansoria.socialappv4.InterfazGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bryansoria.socialappv4.Adapters.AdapterGame;
import com.bryansoria.socialappv4.MainActivity;
import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;
import java.util.List;

public class Biblioteca extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List juegos = new ArrayList();
    private ArrayList  allGames = new <Game>ArrayList();
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inicializamos el action Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId((R.id.biblioteca));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.biblioteca:

                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.post:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //Iniciamos la Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Leemos el array con los juegos o lo inicializamos
        //juegos = StorageConfig.leerListaJuego(getApplicationContext());
        if(juegos==null)
            juegos=new ArrayList();


        //Cargamos todos los juegos en el arrayMain
        cargarJuegos.cargar(allGames);


        //Inicimaos
        init();
    }



    public void init(){
        AdapterGame adapterGame=new AdapterGame(this,allGames);
        RecyclerView recyclerView = findViewById(R.id.lista_juegos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterGame);

        adapterGame.setOnClickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mandarJuego((Game)allGames.get(recyclerView.getChildAdapterPosition(view)));
            }
        });
    }
    public void mandarJuego(Game game){
        Intent i = new Intent(Biblioteca.this, DetalleJuego.class);
        i.putExtra("a", game);
        startActivity(i);
    }



}