package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
/**
 * Created by Siotle on 12/11/2015.
 */
public class HighScoreClearTest extends ActivityInstrumentationTestCase2<MainMenu> {

    MainMenu mainMenu;

    public HighScoreClearTest(){
        super(MainMenu.class);
    }

    public void setUp() throws Exception{
        super.setUp();

    }


    /*
    Given a populated high score table,
    When the user presses "Reset High Scores" in the settings menu,
    Then all high scores and names should reset to their default value.
     */
    public void testClearHighScore(){
        mainMenu = getActivity();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainMenu);
        SharedPreferences.Editor editor = sharedPref.edit();

        for(int i = 0; i < 9; i++){

            editor.putString("hsn0" + (i+1), Character.toString((char) (i+'A')));
            editor.putInt("hs0" + (i+1), (i+1));
        }

        editor.commit();

        onView(withId(R.id.high_scores_button)).perform(click());
        pressBack();
        onView(withId(R.id.settings_button)).perform(click());
        onData(withTitle(R.string.reset_hs_button)).perform(click());
        pressBack();
        onView(withId(R.id.high_scores_button)).perform(click());

        HighScoreManager hsManager = new HighScoreManager(mainMenu);

        String expected_name = "None";
        int expected_value = 0;

        for(int i = 0; i < 9; i++){

            String actual_name = hsManager.getHighScoreName("hsn0" + (i+1));
            int actual_value = hsManager.getHighScoreValue("hs0" + (i+1));

            assertEquals(expected_name, actual_name);
            assertEquals(expected_value, actual_value);
        }
    }
}
