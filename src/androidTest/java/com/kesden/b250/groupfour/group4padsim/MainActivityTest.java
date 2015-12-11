package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import java.lang.reflect.Field;

/**
 * Created by Siotle on 12/2/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mAct;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        mAct = getActivity();
    }

    /*
    Given that the user selects Time Attack and Easy difficulty
    When the user opens up the game (MainActivity)
    Then the game should start a Time Attack game on Easy difficulty.
     */
    public void testGameDifficulty() throws Exception{

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mAct);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("game_mode", "1");
        editor.putString("difficulty", "0");

        editor.commit();

        final int expectedMode = 1;
        final int expectedDifficulty = 0;

        Field field = mAct.getClass().getDeclaredField("mode");
        field.setAccessible(true);
        final int actualMode = field.getInt(mAct);

        field = mAct.getClass().getDeclaredField("difficulty");
        field.setAccessible(true);
        final int actualDifficulty = field.getInt(mAct);

        assertEquals(expectedMode, actualMode);
        assertEquals(expectedDifficulty, actualDifficulty);
    }
}
