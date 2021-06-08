package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePlay extends SurfaceView implements SurfaceHolder.Callback {
    MainThread mainThread;

    public GamePlay(Context context) {
        super(context);
            SurfaceHolder myHolder = getHolder();//el holder va a ser el contenedor que contiene nuestra surfaceView
            myHolder.addCallback(this);
            mainThread = new MainThread(myHolder);
    }
    //Cuando se crea la surfaceView, iniciaremos el hilo principal y el hilo principal llamara a los otros hilos que se ejecutan en segundo plano.
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {//este metodo se llama cuando el gamePlay del juego se inicia(se crea la surfaceView)
        //lo usare para crear e iniciar el mainThread con el cual poder iniciar el juego
        mainThread.setIsRunning(true);
        mainThread.start();

    }
    //este metodo se llama cuando deseamos cambiar el tamaño o la orientacion de la surfaceView por loq ue no lo usare para mi juego
    //ya que este siempre lo mostrare de forma vertical
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { }

    //Con este metodo detenemos el hilo
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //Con la ayuda del método join, el mainThread estaría en espera para que los otros hilos
        // desaparezcan o finalicen la ejecución y, después de eso, el mainThread debera unirse.
        boolean retry = true;
        while(retry){
            try {//hacer esto nos provoca una excepcion por lo que debemos capturarla
                mainThread.setIsRunning(false);
                mainThread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            //Si todos los hilos en segundo plano han desaparecido y el mainThread detiene su ejecucion
            // tenemos que configurar retry para forzar a que el bucle while detenga su ejecucion.
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getGameManager().gameState ==0){
            //Si el usuario toca la pantalla el juego se inicia
            com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getGameManager().gameState = 1;
            com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getSoundPlay().playSwoosh();
        }else {
            com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getSoundPlay().playWing();
        }
        com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getGameManager().spirit.setVelocity(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.JUMP_VELOCITY);//aplicamos la fuerza contraria a la gravedad al tocar la pantalla
        return true;
    }
}
