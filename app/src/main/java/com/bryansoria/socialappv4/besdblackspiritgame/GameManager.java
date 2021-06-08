package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    //Esta clase contendra los metodos encargados de dibujar los objetos en pantalla tal como
    //el espiritu, el score, los tubos o el movimiento del background

    com.bryansoria.socialappv4.besdblackspiritgame.BgImage bgImage;
    Spirit spirit;
    static  int gameState;
    ArrayList<Tube> tubeCollections;
    Random rand;
    int scoreCount; //Variable para guardar la puntuacion del juego
    int winningTube; //determina el obstaculo del tubo ganador
    Paint designPaint = new Paint();

    /*
      gameState == 0 : Juego No iniciado
      gameState == 1 : El juego esta iniciado
      gameState == 2 : juego terminado
   */


    public GameManager() {
        bgImage = new com.bryansoria.socialappv4.besdblackspiritgame.BgImage();
        spirit = new Spirit();
        gameState=0;
        tubeCollections = new ArrayList<>();
        rand = new Random();
        generateTubeObject();
        initScoreVariables();
    }

    public void initScoreVariables(){
        scoreCount=0;
        winningTube=0;
        designPaint = new Paint();//Objeto para pintar el Score
        designPaint.setColor(Color.LTGRAY);
        designPaint.setTextSize(225);
        designPaint.setStyle(Paint.Style.FILL);
        designPaint.setFakeBoldText(true);
        designPaint.setShadowLayer(5.0f, 20.0f,20.0f,Color.BLACK);

    }



    public void generateTubeObject(){ //Genera los obstaculos de forma aleatoria.
        for (int i = 0; i< com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tube_numbers; i++){ //probar cambaindo por meteor_Numbers
            int tubeX = com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_WIDTH_X+i* com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tubeDistance;
            int upTubeCollectionY = com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.minimumTubeCollection_Y;
            rand.nextInt(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.maximumTubeCollection_Y - com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.minimumTubeCollection_Y +1);
            Tube tubeCollection = new Tube(tubeX,upTubeCollectionY);
            tubeCollections.add(tubeCollection);
        }
    }

    public void scrollingTube(Canvas can){
        if (gameState == 1){
            //si esta condicion se cumple el estado del juego pasara a 2, es decir, fin del juego
            if ((tubeCollections.get(winningTube).getXtube()<spirit.getX()+ com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getSpiritWidth())
            &&(tubeCollections.get(winningTube).getUpTubeCollection_Y() > spirit.getY()
            || tubeCollections.get(winningTube).getDownMeteor_Y()< (spirit.getY()+ com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getSpiritHeight()))){
                gameState=2;//gameState=2 =GAME OVER
                com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getSoundPlay().playCrash();
                Context mContext = com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.gameActivityContext;
                Intent mIntent = new Intent(mContext,GameOver.class);
                mIntent.putExtra("score",scoreCount);
                mContext.startActivity(mIntent);
                ((Activity)mContext).finish();
            }
            //comprobamos que el lado izquierdo del espiritu supera la posicion del lado derecho del tubo ganador
            if (tubeCollections.get(winningTube).getXtube()<spirit.getX()- com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getTubeWidth()){
                //si la condicion se cumple sumamos uno al contador que se encarga del score
                scoreCount++;
                winningTube++;
                com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getSoundPlay().playPing();
                //si el tubo ganador es mayor que uno lo volvemos a iniciar a cero
                if (winningTube> com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tube_numbers -1){
                    winningTube=0;
                }
            }
            for (int i = 0; i< com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tube_numbers; i++){
                if (tubeCollections.get(i).getXtube()<-com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getTubeWidth()){
                    tubeCollections.get(i).setXtube(tubeCollections.get(i).getXtube()+
                            com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tube_numbers * com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tubeDistance);
                    int upTubeCollectionY = com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.minimumTubeCollection_Y + rand.nextInt(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.maximumTubeCollection_Y - com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.minimumTubeCollection_Y+1);
                    tubeCollections.get(i).setUpTubeCollection_Y(upTubeCollectionY);
                }
                tubeCollections.get(i).setXtube(tubeCollections.get(i).getXtube()- com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tubeVelocity);
                can.drawBitmap(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getUpTube(), tubeCollections.get(i).getXtube(), tubeCollections.get(i).getUpTube_Y(),null);
                can.drawBitmap(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getDownTube(), tubeCollections.get(i).getXtube(), tubeCollections.get(i).getDownMeteor_Y(),null);
            }
            can.drawText(""+scoreCount, 620,400,designPaint);//pinta el score
        }
    }

    public void spiritAnimation(Canvas canvas){
        //el gameState es 1 por lo que el juego esta iniciado
        if (gameState == 1){
            //SOLO si el juego esta iniciado aplicaremos fuerza de gravedad a nuestro objeto
            //Si la posicion de mi objeto espiritu en la coordenada Y es menor que la altura
            //de la pantalla quiere decir que mi obejto espiritu esta dentro de la pantalla por lo que aplicaremos fuerza de gravedad en el
            if (spirit.getY()<(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_HEIGHT_Y- com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getSpiritHeight()) || spirit.getVelocity()<0){
                spirit.setVelocity(spirit.getVelocity()+ com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.gravityPull);
                spirit.setY(spirit.getY()+spirit.getVelocity());
            }
        }

        int currentFrame = spirit.getCurrentFrame();
        canvas.drawBitmap(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getSpirit(currentFrame),spirit.getX(),spirit.getY(),null);//pintamos el Espiritu
        currentFrame++;
        if (currentFrame > spirit.maximumFrame){
            currentFrame =0;
        }
        spirit.setCurrentFrame(currentFrame);
    }

    public void backgroundAnimation(Canvas canvas){
        bgImage.setX(bgImage.getX()-bgImage.getVelocity());
        if (bgImage.getX()<-com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getBackgroundWidth()){
            bgImage.setX(0); //reiniciamos la posicion de la imagen a su posicion inicial para hacer un scroll infinito del background
        }
        canvas.drawBitmap(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getBackground(),bgImage.getX(),bgImage.getY(),null);//dibujamos con canvas
        if (bgImage.getX() <-(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getBackgroundWidth() - com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_WIDTH_X)){
            canvas.drawBitmap(com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getBackground(), bgImage.getX() +
                    com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getBackgroundWidth(), bgImage.getY(), null);//Cuando la imagen termina volvemos a dibujarla en la posicion inicial
        }
    }
}
