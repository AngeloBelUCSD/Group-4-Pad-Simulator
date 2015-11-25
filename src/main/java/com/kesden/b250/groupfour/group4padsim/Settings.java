package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class Settings extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.game_preferences);

        Preference reset_hs_button = findPreference(getString(R.string.reset_hs_key));

        reset_hs_button.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        String[] hs_keys = {"hs1", "hs2", "hs3", "hs4", "hs5"};

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        for(int i = 0; i < 5; i++){
                            editor.putInt(hs_keys[i], 0);
                        }
                        editor.commit();

                        return true;
                    }
                }
        );
    }



}
