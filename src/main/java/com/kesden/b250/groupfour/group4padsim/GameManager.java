package com.kesden.b250.groupfour.group4padsim;

import android.os.CountDownTimer;

/**
 * Created by Jassa on 11/19/2015.
 */
public class GameManager {
    private OrbMatcher matcher;
    private CountDownTimer dragTimer;
    private boolean endTimer = false;

    public GameManager(OrbMatcher inputMatcher){
        matcher = inputMatcher;
    }

    public int getScore(){
        return matcher.total;
    }

    public void resetScore(){
        matcher.total = 0;
    }

    public void startTimer(){
        endTimer = false;
        dragTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                endTimer = true;
            }
        }.start();
    }

    public boolean getEndTimer(){
        return endTimer;
    }

    public void setEndTimer(boolean setTimerTo){
        endTimer = setTimerTo;
    }
}
