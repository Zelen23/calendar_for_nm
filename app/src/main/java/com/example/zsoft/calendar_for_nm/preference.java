package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by adolf on 17.03.2018.
 * раздел настроек по группам
 * реализовать фрагментами
 * для маленьких экранов переходом
 * для больших двумя фрагментами
 *
 * 1я группа- стили
 * -размер
 * -цвет
 * -фон
 * автопименение при выходе из раздела настроек
 *
 */

public class preference  extends PreferenceActivity{


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.pref_head,target);
    }


    @Override
    protected boolean isValidFragment(String fragmentName) {

        //return  PrefFragment.class.getName().equals(fragmentName);
        return true;
    }


    // фрагменты настоек

    public static class PrefFragment extends PreferenceFragment{


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i("pref__psuse","" +
                    "Pause");

        }
    }

    public static class PrefUserSettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_user);
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i("prefusr__psuse","" +
                    "Pause");

        }

    }






}
