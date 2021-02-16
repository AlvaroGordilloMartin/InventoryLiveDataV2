package com.example.inventorylivedata.iu.dependency;

import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.repository.DependencyRepository;

import java.util.List;

public class ListDependencyInteractorImpl {

    private ListDependencyInteractor callback;


    /**
     * Interfaz que debe implementar el presentador que quiera utilizar el interactor
     */
    interface ListDependencyInteractor{
        void OnNoData();
        void OnSuccess(List<Dependency> list);
        void onSuccessDeleted();

        void onSuccessUndo();
    }

    public ListDependencyInteractorImpl(ListDependencyInteractor callback){
        this.callback = callback;
    }

    /**
     * Método que solicita el conjunto de dependencias al Repository/Origen de datos
     */
    public void load() {
        List<Dependency> list = DependencyRepository.getInstance().get();
        //1. No hay datos en el listado.
        if (list.isEmpty())
            callback.OnNoData();
        else
            callback.OnSuccess(list);
    }


    public void delete(Dependency deleted) {
        DependencyRepository.getInstance().delete(deleted);
        callback.onSuccessDeleted();
    }


    public void undo(Dependency deleted) {
        DependencyRepository.getInstance().add(deleted);
        callback.onSuccessUndo();
    }


}
