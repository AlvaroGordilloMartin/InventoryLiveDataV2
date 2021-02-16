package com.example.inventorylivedata.iu.dependency;

import com.example.inventorylivedata.data.model.Dependency;

import java.util.List;

public class ListDependencyPresenter implements ListDependencyContract.Presenter, ListDependencyInteractorImpl.ListDependencyInteractor {

    private ListDependencyInteractorImpl interactor;
    private ListDependencyContract.View view;

    public ListDependencyPresenter(ListDependencyContract.View view) {
        this.interactor = new ListDependencyInteractorImpl(this);
        this.view = view;
    }

    /**
     * Este método viene del contrato con la vista
     */
    @Override
    public void load() {
        view.showProgress();
        interactor.load();
    }

    @Override
    public void delete(Dependency deleted) {
        interactor.delete(deleted);
    }

    @Override
    public void undo(Dependency deleted) {
        interactor.undo(deleted);
    }

    /**
     * Este método viene de la interfaz de Base presenter
     */
    @Override
    public void onDestroy() {
        this.view = null;
        this.interactor = null;
    }

    /**
     * Este método viene de la interfaz de ListDependencyInteractor
     */
    @Override
    public void OnNoData() {
        view.hideProgress();
        view.setNoData();
    }

    /**
     * Este método viene de la interfaz de ListDependencyInteractor
     */
    @Override
    public void OnSuccess(List<Dependency> list) {
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
