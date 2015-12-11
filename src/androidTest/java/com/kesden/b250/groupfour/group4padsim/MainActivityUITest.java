package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.test.ActivityInstrumentationTestCase2;
import android.text.InputType;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
import static android.support.test.espresso.matcher.PreferenceMatchers.withKey;

/**
 * Created by Siotle on 12/11/2015.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainMenu> {

    MainMenu mainMenu;

    public MainActivityUITest(){
        super(MainMenu.class);
    }

    public void setUp() throws Exception{
        super.setUp();

    }

    public void testModeDifficulty(){

        mainMenu = getActivity();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainMenu);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("p_name", "None");
        editor.putString("game_mode", "0");
        editor.putString("difficulty", "0");

        editor.commit();

        //Given that the user opens the game's settings
        onView(withId(R.id.settings_button)).perform(click());

        //And resets all the high scores
        onData(withTitle(R.string.reset_hs_button)).perform(click());

        //When they set player name to PADSIM, game mode to Time Attack, and difficulty to Hard
        onData(withKey("p_name")).perform(click());
        onView(allOf(hasFocus(), withInputType(InputType.TYPE_CLASS_TEXT))).perform(typeTextIntoFocusedView("PADSIM"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());

        onData(withKey("game_mode")).perform(click());
        onView(withText("Time Attack")).perform(click());

        onData(withKey("difficulty")).perform(click());
        onView(withText("Hard")).perform(click());

        //When the user goes back to the main menu
        //And starts a new game
        pressBack();
        onView(withId(R.id.button)).perform(click());

        final int expectedMode = 1;
        final int expectedDifficulty = 2;

        SettingsManager setManager = new SettingsManager(mainMenu);

        final int actualMode = setManager.getMode();
        final int actualDifficulty = setManager.getDifficulty();

        //Then the difficulty of the game itself should be the same as the settings they chose.
        assertEquals(expectedMode, actualMode);
        assertEquals(expectedDifficulty, actualDifficulty);

        //When the user goes back to the main menu from the Time Attack game
        //And opens up the high scores table
        pressBack();
        onView(withId(R.id.high_scores_button)).perform(click());

        HighScoreManager hsManager = new HighScoreManager(mainMenu);
        String actual_name = hsManager.getHighScoreName("hsn01");
        String expected_name = "PADSIM";

        //Then they should see their name at the top of the list.
        assertEquals(actual_name,expected_name);
    }
}
