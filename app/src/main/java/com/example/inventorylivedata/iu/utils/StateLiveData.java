package com.example.inventorylivedata.iu.utils;

import androidx.lifecycle.MutableLiveData;

/**
 * Esta clase es la que contiene los emtodos que pueden modificar un objeto StateData. Ademas, sera la clase observada por el LifeCycleOwner
 * @param <T>
 */
public class StateLiveData<T> extends MutableLiveData<StateData> {
    public void postLoading() {
        postValue(new StateData().loading());
    }

    public void postSuccess(T data) {
        postValue(new StateData().success(data));
    }

    public void postError(int err_authentication) {
        postValue(new StateData().error(err_authentication));
    }

    public void postComplete(){
        postValue(new StateData().complete());
    }
}
