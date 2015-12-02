package com.kesden.b250.groupfour.group4padsim;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

/**
 * Created by Siotle on 12/2/2015.
 */
public class HighScoreActivityTest extends ActivityInstrumentationTestCase2<HighScoreActivity> {

    HighScoreActivity hiAct;

    public HighScoreActivityTest() {
        super(HighScoreActivity.class);

        hiAct = getActivity();
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(hiAct);
        SharedPreferences.Editor editor = sharedPref.edit();

        for(int i = 0; i < 9; i++){

            editor.putString("hsn0" + (i+1), Character.toString((char) (i+'A')));
            editor.putInt("hs0" + (i+1), (i+1));
        }

        editor.commit();
    }

    /*
    Given that the user chooses to clear all high scores
    When the user opens up HighScoreActivity
    All high scores should show up as cleared.
     */

    public void testClearedHighScore() throws Exception{

        HighScoreManager hsManager = new HighScoreManager(hiAct);
        hsManager.clearHighScores();

        String expected_name = "None";
        int expected_value = 0;

        for(int i = 0; i < 9; i++){

            String actual_name = hsManager.getHighScoreName("hsn0" + i);
            int actual_value = hsManager.getHighScoreValue("hs0" + i);

            assertEquals(expected_name, actual_name);
            assertEquals(expected_value, actual_value);
        }
    }
}
