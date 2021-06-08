package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    //El objetivo de la clase Main Thread es actualizar continuamente la pantalla siempre que la condicion sea verdadera.
    SurfaceHolder mySurfaceHolder;//esto nor permitira usar canvas
    long timeSpent;
    long kickOffTime;
    long WAIT = 31; //tiempo que tardan los frames en actualizarse (en milisegundos)
    boolean Running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder) {
        this.mySurfaceHolder = surfaceHolder;
        Running = true;
    }

    @Override
    public void run() {
        while(Running) {
            kickOffTime = SystemClock.uptimeMillis();
            canvas = null;
            try{
                //Cuando llamamos a la clase MainThread el metodo Run comenzara a ejecutarse, y mientras nuestro hilo se esta ejecutando
                //tambien se veran involucrados varios métodos de la clase GameManager que son los encargados de dibujar todo lo que se mostrara en pantalla
                //por eso usare el metodo Synchronized
                //Si no lo usaramos, debido a que tenemos tantos hilos ejecutándose en segundo plano,
                //intentar llamar a otros, nos podria dar problemas
                synchronized (mySurfaceHolder){
                    //Con la ayuda del metodo synchronized seremos capaces de llamar a solo un hilo al mismo tiempo
                    canvas = mySurfaceHolder.lockCanvas();
                    com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getGameManager().backgroundAnimation(canvas);//llamo a los metodos encargados de dibujar los obejtos en pantalla
                    com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getGameManager().spiritAnimation(canvas);
                    com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getGameManager().scrollingTube(canvas);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null){
                    try{
                        mySurfaceHolder.unlockCanvasAndPost(canvas);//desbloqueamos canvas
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            //controlamos la velocidad de nuestra aplicacion
            timeSpent = SystemClock.uptimeMillis() - kickOffTime;
            if (timeSpent < WAIT){
                try{
                    Thread.sleep(WAIT-timeSpent);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    public boolean isRunning() {
        return Running;
    }

    public void setIsRunning(boolean state) {
        Running = state;;
    }
}
