package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    public static class PrefClearDB extends PreferenceFragment{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v=inflater.inflate(R.layout.pref_clear_db,container,false);
            Button b=v.findViewById(R.id.button22);
            SeekBar intervalClean=v.findViewById(R.id.seekBar);
            final TextView textView=v.findViewById(R.id.textView6);
            intervalClean.setMin(new HelperData()
                    .Intrval_to_seekBar("2016-1-21"));

            intervalClean.setMax(new HelperData()
                    .Intrval_to_seekBar("2018-8-21"));

            textView.setText("0");

            intervalClean.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    textView.setText(new HelperData().fromIntToDateString(seekBar.getProgress()));

                }
            });





            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),""+new HelperData()
                            .Intrval_to_seekBar("2016-1-21"),Toast.LENGTH_SHORT).show();

                }
            });

            return v;
        }
    }






}
