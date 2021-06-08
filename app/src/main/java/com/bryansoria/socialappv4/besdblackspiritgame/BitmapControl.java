package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bryansoria.socialappv4.R;

public class BitmapControl {
    //Esta clase sera la encargada de cargar las intancias de Bitmap y de dar a las imagenes un tamaño correcto
    Bitmap background;
    Bitmap[] Spirit;
    Bitmap upTube;
    Bitmap downTube;

    public BitmapControl(Resources res) {
        //para poder hacer un Bitmap a apartir de un recurse debemos usar BitmapFactory.decodeResource
        background = BitmapFactory.decodeResource(res, R.drawable.background_black);
        background = imageScale(background);//lanzamos el metodo que ajusta nuestra imagen de fondo
        Spirit = new Bitmap[3];
        Spirit[0] = BitmapFactory.decodeResource(res,R.drawable.spirit1);//relacionamos nuestro objeto Bitmap con el recurso dibujable Spirit
        Spirit[1] = BitmapFactory.decodeResource(res,R.drawable.spirit2);
        Spirit[2] = BitmapFactory.decodeResource(res,R.drawable.spirit3);
        upTube = BitmapFactory.decodeResource(res,R.drawable.brown_up);
        downTube = BitmapFactory.decodeResource(res,R.drawable.brown_down);
    }

    public Bitmap getUpTube(){
        return upTube;
    }

    public Bitmap getDownTube(){
        return downTube;
    }

    public int getTubeWidth(){
        return upTube.getWidth();
    }

    public int getTubeHeight(){
        return upTube.getHeight();
    }

    public Bitmap getSpirit(int frame){
        return Spirit[frame];
    }

    public int getSpiritWidth(){
        return Spirit[0].getWidth();
    }

    public int getSpiritHeight(){
        return Spirit[0].getHeight();
    }

    public Bitmap getBackground(){
        return background;
    }

    public int getBackgroundWidth(){
        return background.getWidth();
    }

    public int getBackgroundHeight(){
        return background.getHeight();
    }

    public Bitmap imageScale(Bitmap bitmap) {//ajustamos la imagen que usaremos de background
        //para obtener la escala adecuada debemos dividir el ancho por la altura de la imagen y despues multiplicarla por la altura de la pantalla
        float width_heightRatio = getBackgroundWidth() / getBackgroundHeight();
        int bgScaleWidth = (int) width_heightRatio* com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_WIDTH_X;
        return Bitmap.createScaledBitmap(bitmap, bgScaleWidth, com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_HEIGHT_Y, false);
    }
}
