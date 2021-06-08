package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class AppHolder {
    static BitmapControl bitmapControl;
    static GameManager gameManager;
    static int SCRN_WIDTH_X;
    static int SCRN_HEIGHT_Y;
    static int gravityPull;
    static  int JUMP_VELOCITY;//variable para almacenar la fuerza contraria a la gravedad
    static int tubeGap;
    static int tube_numbers;
    static int tubeVelocity;
    static int minimumTubeCollection_Y;
    static int maximumTubeCollection_Y;
    static int tubeDistance;
    static Context gameActivityContext;
    static Sound soundPlay;

    public static void assign(Context context){

        mapScreenSize(context);//Debe ir al principio del metodo
        bitmapControl = new BitmapControl(context.getResources());
        holdGameVariables();
        gameManager = new GameManager();
        soundPlay = new Sound(context);

    }
    public static Sound getSoundPlay(){
        return soundPlay;
    }

    public static void holdGameVariables(){
        AppHolder.gravityPull = 5;
        AppHolder.JUMP_VELOCITY = -50;//indico la fuerza que aplico
        AppHolder.tubeGap = 800;//separacion entre tubos
        AppHolder.tube_numbers = 2;
        AppHolder.tubeVelocity = 20;//velocidad a la que aparecen los tubos
        AppHolder.minimumTubeCollection_Y = (int) (AppHolder.tubeGap / 2.0);
        AppHolder.maximumTubeCollection_Y = AppHolder.SCRN_HEIGHT_Y-AppHolder.minimumTubeCollection_Y - AppHolder.tubeGap;
        AppHolder.tubeDistance = AppHolder.SCRN_WIDTH_X*2/3;
    }

    public static BitmapControl getBitmapControl(){

        return bitmapControl;
    }

    public static GameManager getGameManager() {

        return gameManager;
    }

    private static void mapScreenSize(Context context){
        //Encargado de organizar la pantalla de la aplicacion
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display dsp = manager.getDefaultDisplay();//Display proporciona informacion acerca del tamaño y la densidad de la pantalla logica
        DisplayMetrics dMetrics = new DisplayMetrics();
        dsp.getMetrics(dMetrics);
        int width = dMetrics.widthPixels;
        int height = dMetrics.heightPixels;
        AppHolder.SCRN_WIDTH_X = width;
        AppHolder.SCRN_HEIGHT_Y = height;
    }
}
