package com.example.inventorylivedata.iu.dependency;

import com.example.inventorylivedata.data.model.Dependency;

public class EditDependencyPresenter implements EditDependencyContract.Presenter, EditDependencyInteractorImpl.EditDependencyInteractor {

    private EditDependencyInteractorImpl interactor;
    private EditDependencyContract.View view;

    public EditDependencyPresenter(EditDependencyContract.View view) {
        this.interactor = new EditDependencyInteractorImpl(this);
        this.view = view;
    }

    @Override
    public void onDestroy() {
        this.interactor = null;
        this.view = null;
    }

    @Override
    public void OnShortNameExists() {
        view.setShortNameExists();
    }

    @Override
    public void OnSuccess() {
        view.onSuccess();
    }


    @Override
    public void furtherDependency(Dependency dependency) {
        interactor.furtherDependency(dependency);
    }

    @Override
    public void editDepedency(Dependency dependency) {
        interactor.editDependency(dependency);
    }
}
