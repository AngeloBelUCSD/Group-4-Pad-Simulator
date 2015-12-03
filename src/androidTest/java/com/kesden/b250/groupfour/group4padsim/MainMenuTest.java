package com.kesden.b250.groupfour.group4padsim;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Siotle on 12/2/2015.
 */
public class MainMenuTest extends ActivityInstrumentationTestCase2<MainMenu> {

    MainMenu mainM;

    public MainMenuTest() {
        super(MainMenu.class);

        mainM = getActivity();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
    Given that the user opens the application
    When the user hits the Settings button
    Then the Settings activity should open.
     */
    public void menuButtonTest() throws Exception {

        mainM.settings(null);
    }
}
