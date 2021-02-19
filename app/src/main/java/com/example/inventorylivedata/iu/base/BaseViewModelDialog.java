package com.example.inventorylivedata.iu.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModelDialog extends ViewModel {
    MutableLiveData<Boolean> confirm;

    public BaseViewModelDialog() {
        this.confirm = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> getConfirm() {
        return confirm;
    }

    public void setConfirm(MutableLiveData<Boolean> confirm) {
        this.confirm = confirm;
    }
}
