package com.example.inventorylivedata.iu.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.iu.InventoryActivity;
import com.example.inventorylivedata.iu.utils.CommonUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private TextInputLayout tilUser;
    private TextInputLayout tilPassword;
    private TextInputLayout tilEmail;
    private TextInputLayout tilConfirmPassword;

    private TextInputEditText tieUser;
    private TextInputEditText tiePassword;
    private TextInputEditText tieEmail;
    private TextInputEditText tieConfirmPassword;

    private SignUpContract.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tieUser = findViewById(R.id.tieUser);
        tiePassword = findViewById(R.id.tiePassword);
        tieConfirmPassword = findViewById(R.id.tieConfirmPassword);
        tieEmail = findViewById(R.id.tieEmail);

        tilUser = findViewById(R.id.tilUser);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        tilEmail = findViewById(R.id.tilEmail);

        //Añadir el listener TextWatcher a los componentes til
        tieUser.addTextChangedListener(new SignUpTextWatcher(tieUser));
        tiePassword.addTextChangedListener(new SignUpTextWatcher(tiePassword));
        tieConfirmPassword.addTextChangedListener(new SignUpTextWatcher(tieConfirmPassword));
        tieEmail.addTextChangedListener(new SignUpTextWatcher(tieEmail));


        presenter = new SignUpPresenter(this);
    }

    /**
     * Metodo que comprueba si es valido el usuario, la contraseña y el email
     */
    public void signUp(View view) {
        presenter.validateUser(tieUser.getText().toString(), tiePassword.getText().toString(), tieConfirmPassword.getText().toString(), tieEmail.getText().toString());
    }

    /**
     * Se da de alta el usuario y pasamos a la pantalla Login
     */
    @Override
    public void onSuccess() {
        Intent intent = new Intent(SignUpActivity.this, InventoryActivity.class);
        startActivity(intent);
    }


    @Override
    public void showProgressDialog() {
        progressDialog = CommonUtils.showLoadingDialog(this);
        //progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void setUserExistsError() {
        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.err_user_exists), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setConfirmPasswordError() {
        tilConfirmPassword.setError(getString(R.string.err_confirmpassword));
        showSoftKeyBoard(tieConfirmPassword);
    }

    @Override
    public void setEmailEmptyError() {
        tilEmail.setError(getString(R.string.err_email_empty));
        showSoftKeyBoard(tieConfirmPassword);
    }

    @Override
    public void setEmailFormatError() {
        tilEmail.setError(getString(R.string.err_email_format));
        showSoftKeyBoard(tieEmail);
    }

    //Estos métodos vienen de UserView
    @Override
    public void setUserEmptyError() {
        tilUser.setError(getResources().getString(R.string.err_user_empty));
        showSoftKeyBoard(tieUser);
    }

    /**
     * Este metodo viene de la interfaz UserView
     */
    @Override
    public void setPasswordFormatError() {
        tilPassword.setError(getResources().getString(R.string.err_password_format));
        showSoftKeyBoard(tiePassword);
    }

    /**
     * Este metodo viene de la interfaz UserView
     */
    @Override
    public void setPasswordEmptyError() {
        tilPassword.setError(getResources().getString(R.string.err_password_empty));
        showSoftKeyBoard(tiePassword);
    }


    @Override
    public void setAuthenticationError() {

    }

    @Override
    protected void onDestroy() {
        presenter = null;
        super.onDestroy();
    }

    //region claseControlError
    class SignUpTextWatcher implements TextWatcher {

        private View view;

        SignUpTextWatcher(View view) {
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
                    validateUser();
                    break;
                case R.id.tiePassword:
                    validatePassword();
                    break;
                case R.id.tieConfirmPassword:
                    validateConfirmPassword();
                    break;
                case R.id.tieEmail:
                    validateEmail();
                    break;

            }
        }
    }

    private void validateUser() {
        if (tieUser.getText().toString().trim().isEmpty()) {
            tilUser.setError(getString(R.string.err_user_empty));
            showSoftKeyBoard(tieUser);
        } else {
            tilUser.setErrorEnabled(false);
        }

    }

    private void validatePassword() {
        if (tiePassword.getText().toString().trim().isEmpty()) {
            tilPassword.setError(getString(R.string.err_password_empty));
            showSoftKeyBoard(tiePassword);

        } else if (tiePassword.getText().toString().length() < 3) {
            tilPassword.setError(getString(R.string.err_password_format));
            showSoftKeyBoard(tiePassword);
        } else {
            tilPassword.setErrorEnabled(false);
        }

    }

    private void validateConfirmPassword() {
        if (!tiePassword.getText().toString().trim().equals(tieConfirmPassword.getText().toString())) {
            tilConfirmPassword.setError(getString(R.string.err_confirmpassword));
            showSoftKeyBoard(tieConfirmPassword);
        } else
            tilConfirmPassword.setErrorEnabled(false);
    }

    private void validateEmail() {
        if (tieEmail.getText().toString().trim().isEmpty()) {
            tilEmail.setError(getString(R.string.err_email_empty));
            showSoftKeyBoard(tieEmail);
        } else {
            tilEmail.setErrorEnabled(false);
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
}