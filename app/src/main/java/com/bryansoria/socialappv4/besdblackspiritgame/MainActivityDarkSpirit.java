package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bryansoria.socialappv4.InterfazGame.HomeActivity;
import com.bryansoria.socialappv4.R;

public class MainActivityDarkSpirit extends AppCompatActivity {

    ImageButton mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_darkspirit);
        com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.assign(this.getApplicationContext());
        mButton = findViewById(R.id.exitBtn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDarkSpirit.this, HomeActivity.class);//Volvemos al home
                startActivity(intent);
            } //Cerramos todas las activities y con ello la aplicacion
        });
    }


    public void startGame(View view){
        Intent intent = new Intent(this, com.bryansoria.socialappv4.besdblackspiritgame.GameActivity.class);//iniciamos el juego
        finish();//Como hemos iniciado el GameActivity ya no me hace falta el MainActivity por lo que lo cierro
        startActivity(intent);
    }
}