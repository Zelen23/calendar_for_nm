package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

/**
 * Created by adolf on 17.03.2018.
 */

public class preference  extends PreferenceActivity{
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference preference=(Preference)findPreference("customPref");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared",
                        Activity.MODE_PRIVATE);

                return true;
            }
        });
    }

}
