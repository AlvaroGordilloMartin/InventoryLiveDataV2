package com.example.inventorylivedata.iu.signup;

import android.os.Handler;
import android.text.TextUtils;

import com.example.inventorylivedata.data.repository.UserRepository;
import com.example.inventorylivedata.iu.utils.CommonUtils;

public class SignUpInteractorImpl {

    private SignUpInteractor presenter;

     /**
     * Definicion de las interfaces que debe implementar cualquier presentador que haga uso de este Interactor
     */
    interface  SignUpInteractor {

        //Usuario duplicado en el sistema
        void onUserExistsError();

        //Error en la confirmación de Password.
        void onConfirmPasswordError();

        //El email no puede ser nulo.
        void onEmailEmptyError();

        //El email debe cumplir un formato correcto.
        void onEmailFormatError();

        //RN-U1 y Alternativa 1.1
        void onUserEmptyError();

        //RN-U1 y Alternativa 1.1
        void onPasswordEmptyError();

        //RN-U2 y Alternativa 1.2
        void onPasswordFormatError();

        //Secuencia normal del caso de uso.
        void onSuccess();
    }

    public SignUpInteractorImpl(SignUpInteractorImpl.SignUpInteractor presenter) {
        this.presenter = presenter;
    }


    public void validateUser(final String user, final String password, final String confirmPassword, final String email) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Reglas de negocio y  Alternativas del caso de uso UC1-Login
                //RN-U1 y Alternativa 1.1.: el usuario no puede ser nulo
                if (TextUtils.isEmpty(user)) {
                    presenter.onUserEmptyError();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    presenter.onPasswordEmptyError();
                    return;
                }
                if (!CommonUtils.isPasswordValid(password))
                {
                    presenter.onPasswordFormatError();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    presenter.onConfirmPasswordError();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    presenter.onEmailEmptyError();
                    return;
                }
                if (!CommonUtils.isEmailValid(email)){
                    presenter.onEmailFormatError();
                    return;
                }
                if (UserRepository.getInstance().userExists(user)){
                    presenter.onUserExistsError();
                    return;
                }
                UserRepository.getInstance().add(user,password,email);
                //Caso de éxito
                presenter.onSuccess();
            }
        }, 2000);
    }
}
