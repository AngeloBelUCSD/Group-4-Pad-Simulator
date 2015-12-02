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

        int totalHighScores = getResources().getInteger(R.integer.total_high_scores);
        String[] text_view_ids = getResources().getStringArray(R.array.hs_keys);

        HighScoreManager hs_manager = new HighScoreManager(this);

        int[] hs = hs_manager.getAllHighScores();
        String[] hsn = hs_manager.getAllHighScoreNames();

        for(int i = 0; i < totalHighScores; i++){

            int view_id = getResources().getIdentifier(text_view_ids[i], "id", getPackageName());
            TextView tv = (TextView) findViewById(view_id);

            tv.setText((i+1) + ". " + hsn[i] + " - " + hs[i]);
        }
    }
}
