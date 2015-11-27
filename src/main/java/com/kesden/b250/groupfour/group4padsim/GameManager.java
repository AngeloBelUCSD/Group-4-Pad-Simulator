package com.kesden.b250.groupfour.group4padsim;

/**
 * Created by Jassa on 11/19/2015.
 */
public class GameManager {
    private OrbMatcher matcher;

    public GameManager(OrbMatcher inputMatcher){
        matcher = inputMatcher;
    }

    public int getScore(){
        return matcher.total;
    }

    public void resetScore(){
        matcher.total = 0;
    }
}
