package com.example.inventorylivedata.iu.login;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.User;
import com.example.inventorylivedata.data.repository.UserRepository;
import com.example.inventorylivedata.iu.utils.StateLiveData;

/**
 * Clase que controla la reglas de negocio de la clase User
 */
public class LoginViewModel extends ViewModel {

    MutableLiveData<String> user;
    MutableLiveData<String> password;
    StateLiveData<User> userState;

    public LoginViewModel() {
        user = new MutableLiveData<>();
        password = new MutableLiveData<>();
        userState = new StateLiveData<>();
    }

    public MutableLiveData<String> getUser() {
        return user;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public StateLiveData<User> getUserState() {
        return userState;
    }

    public void validateCredentials() {
        //1. Estoy haciendo una peticion larga en el tiempo -> mostrar ProgressBar
        userState.postLoading();
        //2. Realizo peticion larga
        if(UserRepository.getInstance().validateCredentials(user.getValue(), password.getValue())){
            //1.1. Peticion correcta
            userState.postSuccess(new User(user.getValue(),password.getValue()));
        }else
            //1.2. Error
            userState.postError(R.string.err_authentication);

        //NO PUEDO COMPLETAR LA PETICION PORQUE MODIFICO EL OBJETO userState
    }



}