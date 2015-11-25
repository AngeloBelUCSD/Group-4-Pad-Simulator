package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        updateHighScores();
    }

    public void updateHighScores(){
        int hs1, hs2, hs3, hs4, hs5;
        hs1 = hs2 = hs3 = hs4 = hs5 = 0;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        hs1 = sharedPref.getInt("hs1", 0);
        hs2 = sharedPref.getInt("hs2", 0);
        hs3 = sharedPref.getInt("hs3", 0);
        hs4 = sharedPref.getInt("hs4", 0);
        hs5 = sharedPref.getInt("hs5", 0);

        ((TextView) findViewById(R.id.hs1)).setText("1. " + hs1);
        ((TextView) findViewById(R.id.hs2)).setText("2. " + hs2);
        ((TextView) findViewById(R.id.hs3)).setText("3. " + hs3);
        ((TextView) findViewById(R.id.hs4)).setText("4. " + hs4);
        ((TextView) findViewById(R.id.hs5)).setText("5. " + hs5);
    }

    public void newHighScore(int newHS){
        int[] hs = new int[5];
        String[] hs_keys = {"hs1", "hs2", "hs3", "hs4", "hs5"};
        int[] new_hs = new int[5];

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        for(int i = 0; i < 5; i++){

            hs[i] = sharedPref.getInt(hs_keys[i], 0);

        }
        if(newHS < hs[4]) return;

        int replace = -1;
        for(int i = 0; i < 5; i++){
            if(hs[i] > newHS) new_hs[i] = hs[i];
            if(hs[i] <= newHS){
                if(replace == -1){

                    replace = hs[i];
                    hs[i] = newHS;

                }else{

                    int temp = replace;
                    replace = hs[i];
                    hs[i] = temp;
                }
            }
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        for(int i = 0; i < 5; i++) {

            editor.putInt(hs_keys[i], new_hs[i]);
        }
        editor.commit();
    }
}
