package com.kesden.b250.groupfour.group4padsim;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Siotle on 12/1/2015.
 */
public class SettingsManager {

    Context context;

    public SettingsManager(Context context){

        this.context = context;
    }

    //Returns the setting for the game mode.
    //Endless = 0
    //Time Attack = 1
    public int getMode(){

        int settingValue = 0;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        settingValue = Integer.parseInt(sharedPref.getString("game_mode","0"));

        return settingValue;
    }

    //Returns the difficulty of the game.
    //Easy = 0
    //Medium = 1
    //Hard = 2
    public int getDifficulty(){

        int diffValue = 0;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        diffValue = Integer.parseInt(sharedPref.getString("difficulty","0"));

        return diffValue;
    }

    public String getPlayerName(){

        String player_name = "None";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        player_name = sharedPref.getString("p_name","None");

        return player_name;
    }
}
