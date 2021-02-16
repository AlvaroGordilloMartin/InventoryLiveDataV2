package com.example.inventorylivedata.iu.secciones;

import com.example.inventorylivedata.data.model.Seccion;
import com.example.inventorylivedata.data.repository.SeccionesRepository;

import java.util.List;

public class ListSeccionesInteractorImpl {

    private ListSeccionesInteractor callback;


    /**
     * Interfaz que debe implementar el presentador que quiera utilizar el interactor
     */
    interface ListSeccionesInteractor {
        void OnNoData();

        void OnSuccess(List<Seccion> list);

        void onSuccessDeleted();

        void onSuccessUndo();
    }

    public ListSeccionesInteractorImpl(ListSeccionesInteractor callback) {
        this.callback = callback;
    }

    /**
     * Método que solicita el conjunto de dependencias al Repository/Origen de datos
     */
    public void load() {
        List<Seccion> list = SeccionesRepository.getInstance().get();
        //1. No hay datos en el listado.
        if (list.isEmpty())
            callback.OnNoData();
        else
            callback.OnSuccess(list);
    }


    public void delete(Seccion deleted) {
        SeccionesRepository.getInstance().delete(deleted);
        callback.onSuccessDeleted();
    }


    public void undo(Seccion deleted) {
        SeccionesRepository.getInstance().add(deleted);
        callback.onSuccessUndo();
    }
}
