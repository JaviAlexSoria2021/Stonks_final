package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

public class Spirit {
    private int spiritX;
    private int spiritY;
    private int currentFrame; //posicion actual del espiritu (con posicion me refiero a los sprites del spiritu)
    private int velocity;
    public static int maximumFrame;//posicion final del espiritu

    public Spirit() {
        //ajustamos al centro a nuestro espiritu
        spiritX = com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_WIDTH_X/2 - com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getSpiritWidth()/2;
        spiritY = com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.SCRN_HEIGHT_Y/2 - com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getSpiritHeight()/2;

        currentFrame = 0;
        maximumFrame = 2;

    }

    public int getCurrentFrame() {

        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {

        this.currentFrame = currentFrame;
    }

    public int getX() {

        return spiritX;
    }

    public void setX(int spiritX) {

        this.spiritX = spiritX;
    }

    public int getY() {

        return spiritY;
    }

    public void setY(int spiritY) {

        this.spiritY = spiritY;
    }

    public int getVelocity() {

        return velocity;
    }

    public void setVelocity(int velocity) {

        this.velocity = velocity;
    }
}
