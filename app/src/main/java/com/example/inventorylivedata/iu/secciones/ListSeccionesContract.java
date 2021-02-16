package com.example.inventorylivedata.iu.secciones;

import com.example.inventorylivedata.data.model.Seccion;
import com.example.inventorylivedata.iu.base.BaseListView;
import com.example.inventorylivedata.iu.base.BasePresenter;

public interface ListSeccionesContract {

    interface View extends BaseListView<Seccion> {

        //Método que muestra una barra de progreso en la interfaz
        //mientras se realiza una acción en el interactor
        void showProgress();

        //Método que oculta la barra de progreso en la interfaz
        void hideProgress();

        //Activa la parte de la vista que indica que no hay datos.
        void setNoData();

        void onSuccessDeleted();

        void onSuccessUndo();
    }

    interface Presenter extends BasePresenter {

        void load();

        void delete(Seccion deleted);

        void undo(Seccion deleted);
    }
}
