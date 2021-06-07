package com.bryansoria.socialappv4.InterfazGame;



import com.bryansoria.socialappv4.Model.Game;
import com.bryansoria.socialappv4.R;

import java.util.ArrayList;
import java.util.List;

public class cargarJuegos {


    public static List cargar(ArrayList l){
        Game gameOne = new Game("Galleta Cliker","Alejandro Vallejo","Subgénero de juegos casual donde el jugador tan sólo tiene que hacer \"click\" repetidamente (u otra acción equivalente) para obtener puntos", R.drawable.clikergalleta,"Arcade",1);
        Game gameTwo = new Game("Dark Spirit Game", "Bryan Soria","Su funcionamiento consiste en mantener volando a un pequeño Dark Spirit pixelado que ha de pasar entre tuberías",R.drawable.blackbird,"Arcade",2);
        Game gameThree =new Game("Tetris","Mijaíl Gorbachov","El tetris consiste en ir encajando piezas de diferentes formas y tamaños que caen desde la parte superior de la pantalla para completar un muro sin dejar huecos.",R.drawable.tetris,"Arcade",3);
        Game gameFour =new Game("Snake","Alexey Patitnov","Una línea formada por cuadros o píxeles se mueve por la pantalla. Si no tienes buenos reflejos, esa línea chocará contra los bordes de la pantalla y perderás la partida. Y para obtener puntos, deberás recopilar los cuadros que encuentres a tu paso, lo que a su vez hace más larga esa línea.",R.drawable.snake,"Arcade",4);
        Game gameFive =new Game("Clash Royale","Supercell","Clash Royale es un juego de cartas en el que puedes ver en tiempo real cómo los personajes de tu baraja, basados en el universo del Clash of Clans, se enfrentan a las tropas de tu rival, a quien tendrás que derrotar derribando sus torres con tus mejores estrategias de ataque y defensa.",R.drawable.clashroyale ,"Juego de Cartas",5);
        Game gameSix =new Game("Terraria","Re-Logic","Terraria es un videojuego de mundo abierto en 2D. Contiene elementos de construcción, exploración, aventura y combate, muy similar a juegos clásicos de la consola Super NES, como por ejemplo la serie Metroid, y a otras entregas más recientes como Minecraft. El juego comienza en un mundo creado de forma aleatoria.",R.drawable.terraria,"Sandbox",6);
        Game gameSeven =new Game("Genshin Impact","myHoYo","Genshin Impact es un juego de acción ARPG de mundo abierto free-to-play con una mecánica de monetización de Gacha para conseguir elementos adicionales como personajes especiales y armas.",R.drawable.genshin,"RPG, Acción, Aventura, Sandbox",7);

        l.add(gameOne);
        l.add(gameTwo);
        l.add(gameThree);
        l.add(gameFour);
        l.add(gameFive);
        l.add(gameSix);
        l.add(gameSeven);

        return l;
    }
}
