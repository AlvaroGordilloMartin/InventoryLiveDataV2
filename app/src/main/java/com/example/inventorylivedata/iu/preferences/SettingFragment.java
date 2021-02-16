package com.example.inventorylivedata.iu.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.inventorylivedata.R;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preferences);
        initPreferenceAccount();
        //Se quiere recoger el evento onSharedPreferenceChanged cuando la preferencia lista cambie
        onSharedPreferenceChanged(PreferenceManager.getDefaultSharedPreferences(getContext()),
                getString(R.string.key_ringtone_notification));
    }

    private void initPreferenceAccount() {
        Preference accountPreferences=getPreferenceManager().findPreference(getString(R.string.key_account));
        accountPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                NavHostFragment.findNavController(SettingFragment.this)
                        .navigate(R.id.action_settingFragment_to_accountFragment,null);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //Se registra el listener (el propio fragment) en el PreferenceManager
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        //Eliminamos el registro del listener en PreferenceManager
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (key.equals(getString(R.string.key_ringtone_notification))){
            //La preferencia que ha lanzado el evento es la lista, por tanto
            //podemos hacer un doncasting sin problemas
            ListPreference listPreference = (ListPreference)preference;

            //Vamos a obtenerl el índice del valor seleccionado en sharedPreferences
            int index = listPreference.findIndexOfValue(sharedPreferences.getString(key,""));

            //Vemos si el valor es mayor a 0, por tanto existe un valor y se modifica el summary
            if (index>=0)
                preference.setSummary(listPreference.getEntries()[index]);
            else
                preference.setSummary(sharedPreferences.getString(key,""));
        }
    }
}
