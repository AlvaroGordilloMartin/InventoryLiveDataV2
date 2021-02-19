package com.example.inventorylivedata.iu.dependency;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.repository.DependencyRepository;

import java.util.List;

public class ListDependencyViewModel extends ViewModel {
    MutableLiveData<List<Dependency>> dependencies;

    //Para trabajar con DataBinding voy a declarar dos propiedades

    public LiveData<Integer> size;
    public LiveData<Boolean> empty;

    public ListDependencyViewModel() {
        this.dependencies = new MutableLiveData<>(DependencyRepository.getInstance().get());

        size = new MutableLiveData<>(dependencies.getValue().size());
        empty = new MutableLiveData<>(dependencies.getValue().isEmpty());
    }

    public MutableLiveData<List<Dependency>> getDependencies() {
        return dependencies;
    }

    public LiveData<Integer> getSize() {
        return size;
    }

    public LiveData<Boolean> getEmpty() {
        return empty;
    }

    public void delete(Dependency deleted){

    }

    public void undo(Dependency deleted){

    }
}
