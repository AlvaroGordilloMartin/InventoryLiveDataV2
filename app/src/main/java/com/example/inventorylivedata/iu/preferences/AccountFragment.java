package com.example.inventorylivedata.iu.preferences;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.inventorylivedata.R;

public class AccountFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.account_preferences);
        initPreferencesUser();
        initPreferencesPassword();
    }

    private void initPreferencesPassword() {
        EditTextPreference epPassword = getPreferenceManager().findPreference(getString(R.string.key_password));
        epPassword.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setSingleLine(true);
                editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.selectAll();
            }
        });
    }

    /**
     * En la librería de compatibilidad androidx no funcionan los atributos XML que definen el estilo
     * del componente EdtiText. Este método inicializa dichos atributos en código
     */
    private void initPreferencesUser() {
        EditTextPreference epUser = getPreferenceManager().findPreference(getString(R.string.key_user));
        epUser.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setSingleLine(true);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.selectAll();
            }
        });
    }
}
