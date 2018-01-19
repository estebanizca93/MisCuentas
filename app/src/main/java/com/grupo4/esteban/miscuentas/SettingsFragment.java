package com.grupo4.esteban.miscuentas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Esteban on 26/12/2017.
 */

public class SettingsFragment extends PreferenceFragment {
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onStart() {
            super.onStart();
            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }
}
