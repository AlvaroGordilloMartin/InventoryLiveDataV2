package com.example.inventorylivedata.iu.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.User;
import com.example.inventorylivedata.databinding.ActivityLoginBinding;
import com.example.inventorylivedata.iu.InventoryActivity;
import com.example.inventorylivedata.iu.preferences.InventoryPreferences;
import com.example.inventorylivedata.iu.signup.SignUpActivity;
import com.example.inventorylivedata.iu.utils.CommonUtils;
import com.example.inventorylivedata.iu.utils.StateData;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUser;
    private TextInputLayout tilPassword;
    private ProgressBar progressBar;
    private TextInputEditText tieUser;
    private TextInputEditText tiePassword;

    LoginViewModel loginViewModel;

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewmodel(loginViewModel);

        /*progressBar = findViewById(R.id.progressBar);
        tilUser = findViewById(R.id.tilUser);
        tilPassword = findViewById(R.id.tilPassword);
        tieUser = findViewById(R.id.tieUser);

         */
        binding.tieUser.addTextChangedListener(new LoginTextWatcher(tieUser));
        //binding.tiePassword = findViewById(R.id.tiePassword);
        binding.tiePassword.addTextChangedListener(new LoginTextWatcher(tiePassword));


        //Se tiene que crear la vinculacion owner-observer
        loginViewModel.getUser().observe(this, user -> {
            //Aqui vamos a comprobar las reglas de negocio del objeto User
            validateUser(user);
        });

        loginViewModel.getPassword().observe(this, s -> validatePassword(s));
        loginViewModel.getUserState().observe(this, this::handleUser);
    }

    private void handleUser(StateData stateData) {
        switch (stateData.getStatus()) {
            case LOADING:
                showProgress();
                break;
            case SUCCESS:
                onSuccess();
                loginViewModel.getUserState().postComplete();
                break;
            case ERROR:
                showError(getString(stateData.getError()));
                loginViewModel.getUserState().postComplete();
                break;
            case COMPLETE:
                hideProgress();
                break;
        }
    }

    public void showSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Metodo que valida si el inicio de sesion es correcto
     *
     * @param view
     */
    public void validateUser(View view) {
        showProgress();
        //La validacion del formato, para que no aparezca el error en tiempo de escritura
        loginViewModel.validateCredentials();
    }


    /**
     * Este metodo no se hereda del contrato sino que la vista es reponsable de mostrar o no el progressbar
     */

    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Este metodo viene del contrato LoginContract.View
     */
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }


    private void showError(String message) {
        //1. Inflar la vista snackbar_view.xml
        View view = ((LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.snackbar_view, null);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        tvMessage.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        tvMessage.setText(message);

        //2. Vamos a crear un objeto Snackbar genérico
        //Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.llcontainer), "", Snackbar.LENGTH_SHORT);

        //3. Vamos a personalizar el Layout Snackbar
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(5, 0, 5, 0);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        //4. Añadimos nuestro objeto View al layout
        layout.addView(view);

        //5. Se muestra el snackbar
        snackbar.show();
    }

    /**
     * Este metodo viene de la interfaz baseview
     */
    public void onSuccess() {
        //Sólo cuando el login es correcto se escribe el usuario en las preferencias
        InventoryPreferences.getInstance().putUser(binding.tieUser.getText().toString()
                , binding.tiePassword.getText().toString());
        Intent intent = new Intent(LoginActivity.this, InventoryActivity.class);
        startActivity(intent);
    }


    //region claseControlError
    class LoginTextWatcher implements TextWatcher {

        private View view;

        LoginTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.tieUser:
                    loginViewModel.getUser().setValue(binding.tieUser.getText().toString());
                    break;
                case R.id.tiePassword:
                    loginViewModel.getPassword().setValue(binding.tiePassword.getText().toString());
                    break;

            }
        }
    }

    private void validateUser(String user) {
        if (binding.tieUser.getText().toString().trim().isEmpty()) {
            binding.tilUser.setError(getString(R.string.err_user_empty));
            showSoftKeyBoard(binding.tieUser);
        } else {
            binding.tilUser.setErrorEnabled(false);
        }

    }

    private void validatePassword(String password) {
        if (binding.tiePassword.getText().toString().trim().isEmpty()) {
            binding.tilPassword.setError(getString(R.string.err_password_empty));
            showSoftKeyBoard(binding.tiePassword);

        } else if (CommonUtils.isPasswordValid(binding.tiePassword.getText().toString())) {
            binding.tilPassword.setError(getString(R.string.err_password_format));
            showSoftKeyBoard(binding.tiePassword);
        } else {
            binding.tilPassword.setErrorEnabled(false);
        }

    }
    //endregion

    /**
     * Este método pone el foco en la vista y habilita el teclado virtual
     *
     * @param view
     */
    public void showSoftKeyBoard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * este método oculta el teclado virtual
     *
     * @param view
     */
    public void hideSoftKeyBoard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}