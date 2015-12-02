package com.kesden.b250.groupfour.group4padsim;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Siotle on 12/1/2015.
 */
public class HighScoreManager {

    Context context;

    public HighScoreManager(Context context){

        this.context = context;
    }

    public void updateHighScores(String newName, int newScore){

        Log.d(this.getClass().toString(), "New high score: " + newName + " - " + newScore);

        //Get the stored total number of high scores from resources
        int totalHS = context.getResources().getInteger(R.integer.total_high_scores);

        //Declare 2 int arrays, one holds current high scores.
        //The other holds the high scores after the new one has been factored in.
        int[] hs = new int[totalHS];
        int[] new_hs = new int[totalHS];

        //Same with 2 string arrays.
        String[] hsn = new String[totalHS];
        String[] new_hsn = new String[totalHS];

        //Get an array of keys for the high scores and their attached names from resources.
        String[] hs_keys = context.getResources().getStringArray(R.array.hs_keys);
        String[] hsn_keys = context.getResources().getStringArray(R.array.hsn_keys);

        //Read the high scores and their names.
        for(int i = 0; i < totalHS; i++){

            hs[i] = getHighScoreValue(hs_keys[i]);
            hsn[i] = getHighScoreName(hsn_keys[i]);

        }
        //If the new score is lower than the very last one then high score table is not updated.
        if(newScore < hs[totalHS-1]) return;

        //Declare an int and String to hold high score values as they are pushed down the table.
        int replaceValue = -1;
        String replaceName = "";

        //If a given high score is higher than the new one, move on.
        //If a given high score is less than or equal to a new one, and it is the first high
        //score to be lower or equal, replace it with the new high score + name and shift the
        //previous ones into the next one. Continue for the rest.
        for(int i = 0; i < totalHS; i++){
            if(hs[i] > newScore){

                new_hs[i] = hs[i];
                new_hsn[i] = hsn[i];
            }else{
                if(replaceValue == -1){

                    Log.d(this.getClass().toString(), "New high score at pos " + i + ": " + newName + " - " + newScore);

                    replaceValue = hs[i];
                    replaceName = hsn[i];

                    new_hsn[i] = newName;
                    new_hs[i] = newScore;
                }else{

                    new_hs[i] = replaceValue;
                    new_hsn[i] = replaceName;

                    replaceName = hsn[i];
                    replaceValue = hs[i];
                }
            }
        }

        //Write the new high score table to storage.
        for(int i = 0; i < totalHS; i++) {

            editHighScore(new_hsn[i], new_hs[i], hsn_keys[i], hs_keys[i]);
        }
    }

    //Clear all high scores.
    public void clearHighScores(){

        Resources res = context.getResources();

        //Get the stored total number of high scores from resources
        int totalHS = res.getInteger(R.integer.total_high_scores);

        String defaultName = res.getString(R.string.hsn_default);
        int defaultValue = res.getInteger(R.integer.hs_default);

        //Get an array of keys for the high scores and their attached names from resources.
        String[] hs_keys = res.getStringArray(R.array.hs_keys);
        String[] hsn_keys = res.getStringArray(R.array.hsn_keys);

        for(int i = 0; i < totalHS; i++){

            editHighScore(defaultName, defaultValue, hsn_keys[i], hs_keys[i]);
        }
    }

    public int[] getAllHighScores(){

        //Get the stored total number of high scores from resources
        int totalHS = context.getResources().getInteger(R.integer.total_high_scores);

        int[] hs = new int[totalHS];

        //Get an array of keys for the high scores from resources.
        String[] hs_keys = context.getResources().getStringArray(R.array.hs_keys);

        //Get the array of high score values.
        for(int i = 0; i < totalHS; i++){

            hs[i] = getHighScoreValue(hs_keys[i]);
        }

        return hs;
    }

    public String[] getAllHighScoreNames(){

        //Get the stored total number of high scores from resources
        int totalHS = context.getResources().getInteger(R.integer.total_high_scores);

        String[] hsn = new String[totalHS];

        //Get an array of keys for the high score names from resources.
        String[] hsn_keys = context.getResources().getStringArray(R.array.hsn_keys);

        //Get the array of high score values.
        for(int i = 0; i < totalHS; i++){

            hsn[i] = getHighScoreName(hsn_keys[i]);
        }

        return hsn;
    }

    //Return the high score for a given key.
    public int getHighScoreValue(String key){

        int high_score;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        high_score = sharedPref.getInt(key, 0);

        return high_score;
    }

    //Return the name for a given high score.
    public String getHighScoreName(String key){

        String player_name;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        player_name = sharedPref.getString(key, "");

        return player_name;
    }

    //Pass in new values to a given high score set.
    public void editHighScore(String newName, int newValue, String nameKey, String valueKey){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(valueKey, newValue);
        editor.putString(nameKey, newName);
        editor.commit();

    }
}
