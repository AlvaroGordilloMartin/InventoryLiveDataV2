package com.example.inventorylivedata.iu.dependency;

import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.iu.base.BaseListView;
import com.example.inventorylivedata.iu.base.BasePresenter;

public interface EditDependencyContract {
    interface View extends BaseListView<Dependency> {

        void setShortNameExists();

        void onSuccess();

    }

    interface Presenter extends BasePresenter {

        void furtherDependency(Dependency dependency);
        void editDepedency(Dependency dependency);

    }
}
