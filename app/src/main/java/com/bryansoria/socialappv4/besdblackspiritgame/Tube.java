package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import java.util.Random;

public class Tube {
    private int xTube;
    private int upTubeCollection_Y;
    private Random rand;

    public Tube(int xTube, int upTubeCollection_Y) {
        this.xTube = xTube;
        this.upTubeCollection_Y = upTubeCollection_Y;
        rand = new Random();
    }

    public int getUpTubeCollection_Y() {

        return upTubeCollection_Y;
    }

    public int getXtube() {

        return xTube;
    }

    public int getUpTube_Y(){
        return upTubeCollection_Y- com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.getBitmapControl().getTubeHeight();
    }

    public int getDownMeteor_Y(){
        return upTubeCollection_Y+ com.bryansoria.socialappv4.besdblackspiritgame.AppHolder.tubeGap;
    }

    public void setXtube(int x_Tube) {
        this.xTube = x_Tube;
    }


    public void setUpTubeCollection_Y(int upTubeCollection_Y) {
        this.upTubeCollection_Y = upTubeCollection_Y;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }
}