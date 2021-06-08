package com.bryansoria.socialappv4.besdblackspiritgame;
/*
AUTOR BRYAN SORIA DEFAZ
*/

import android.content.Context;
import android.media.MediaPlayer;

import com.bryansoria.socialappv4.R;

public class Sound {
    Context context;
    MediaPlayer move,score,crash,jump;

    public Sound(Context context) {
        this.context = context;
        move = MediaPlayer.create(context, R.raw.swoosh);
        crash = MediaPlayer.create(context,R.raw.hit);
        jump = MediaPlayer.create(context,R.raw.wing);
        score = MediaPlayer.create(context,R.raw.ping);
    }

    public void playSwoosh(){
        if (move != null){
            move.start();
        }
    }

    public void playPing(){
        if (score != null){
            score.start();
        }
    }

    public void playCrash(){
        if (crash != null){
            crash.start();
        }
    }

    public void playWing(){
        if (jump != null){
            jump.start();
        }
    }
}
