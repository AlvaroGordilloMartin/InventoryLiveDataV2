package com.example.inventorylivedata.iu.secciones;

import com.example.inventorylivedata.data.model.Seccion;

import java.util.List;

public class ListSeccionesPresenter implements ListSeccionesContract.Presenter, ListSeccionesInteractorImpl.ListSeccionesInteractor {

    private ListSeccionesInteractorImpl interactor;
    private ListSeccionesContract.View view;

    public ListSeccionesPresenter(ListSeccionesContract.View view) {
        this.view = view;
        interactor = new ListSeccionesInteractorImpl(this);
    }

    @Override
    public void load() {
        view.showProgress();
        interactor.load();
    }

    @Override
    public void delete(Seccion deleted) {
        interactor.delete(deleted);

    }

    @Override
    public void undo(Seccion deleted) {
        interactor.undo(deleted);

    }

    @Override
    public void onDestroy() {
        this.view = null;
        this.interactor = null;
    }


    @Override
    public void OnNoData() {
        view.hideProgress();
        view.setNoData();
    }

    @Override
    public void OnSuccess(List<Seccion> list) {
        view.hideProgress();
        view.onSuccess(list);
    }

    @Override
    public void onSuccessDeleted() {
        view.onSuccessDeleted();

    }

    @Override
    public void onSuccessUndo() {
        view.onSuccessUndo();
    }
}
