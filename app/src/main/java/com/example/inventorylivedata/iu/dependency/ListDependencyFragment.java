package com.example.inventorylivedata.iu.dependency;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.repository.DependencyRepository;
import com.example.inventorylivedata.iu.adapter.DependencyAdapter;
import com.example.inventorylivedata.iu.base.BaseDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListDependencyFragment extends Fragment implements ListDependencyContract.View {

    private LinearLayout llLoading;
    private LinearLayout llNoData;
    private RecyclerView rvDependency;
    private DependencyAdapter adapter;
    private List<Dependency> list;
    DependencyRepository repository = new DependencyRepository();
    private ListDependencyContract.Presenter presenter;
    private DependencyAdapter.OnDepedencyClickListener listener;
    private Bundle bundle;
    private NavController navController;
    private Dependency deleted;
    FloatingActionButton button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true); Funciona si trabajamos con manager

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_dependency, container, false);
        list = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llLoading = view.findViewById(R.id.llLoading);
        llNoData = view.findViewById(R.id.llNoData);
        rvDependency = view.findViewById(R.id.rvDependency);
        button = view.findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_listDependencyFragment_to_addDependencyFragment);
            }
        });

        navController = Navigation.findNavController(view);
        listener = new DependencyAdapter.OnDepedencyClickListener() {
            @Override
            public void onClick(View v) {
                Dependency dependency;
                onEditDepedencyFragment(dependency = adapter.getItem(rvDependency.getChildAdapterPosition(v)));
            }

            @Override
            public boolean onLongClick(View v) {
                deleted = adapter.getItem(rvDependency.getChildAdapterPosition(v));
                onDeleteDependency(v);
                return true;
            }
        };

        list = repository.get();

        //1. Crear el adapter
        adapter = new DependencyAdapter(list, listener);

        //2. Crear el diseño del RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        //3. Se asigna el diseño al RecyclerView
        rvDependency.setLayoutManager(layoutManager);

        //4. Vincular la vista al modelo
        rvDependency.setAdapter(adapter);

        // Se inicializa el presenter
        presenter = new ListDependencyPresenter(this);
    }

    public void onEditDepedencyFragment(Dependency dependency) {
        bundle = new Bundle();
        bundle.putSerializable(dependency.TAG, dependency);

        NavHostFragment.findNavController(this).navigate(R.id.editDependencyFragment, bundle);
    }

    public void onDeleteDependency(View v) {
        Dependency dependency = adapter.getItem(rvDependency.getChildAdapterPosition(v));
        Bundle bundle = new Bundle();
        bundle.putSerializable("dependencia",dependency);
        bundle.putString(BaseDialogFragment.TITLE, getString(R.string.title_delete_dependency));
        bundle.putString(BaseDialogFragment.MESSAGE
                , String.format(getString(R.string.message_delete_dependency), dependency.getShortname()));

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_listDependencyFragment_to_baseDialogFragment, bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();

        if(getArguments() !=null)
            if(getArguments().getBoolean(BaseDialogFragment.CONFIRM_DELETE)){
                //deleted = (Dependency) getArguments().getSerializable();
                repository.delete(deleted);
                showSnackBarDeleted();
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //presenter
    }

    /**
     * Método que muestras el LineasLayout que contiene el progressbar
     */
    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setNoData() {
        llNoData.setVisibility(View.VISIBLE);
    }

    /**
     * Este método es el que se ejecuta cuando se elimina correctamente una dependencia de la bd
     * y muestra un snackbar con la opción Undo
     */
    @Override
    public void onSuccessDeleted() {
        adapter.delete(deleted);
        showSnackBarDeleted();
    }

    private void showSnackBarDeleted() {
        Snackbar.make(getView(),getString(R.string.confirm_delete_dependency),Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        undoDeleted();
                    }
                }).show();

    }




    /**
     * Deshace el borrado de la dependencia
     */
    private void undoDeleted() {
        presenter.undo(deleted);
    }


    /**
     * Método que inserta una dependencia previamente eliminada
     */
    @Override
    public void onSuccessUndo(){
        adapter.add(deleted);
    }

    @Override
    public void onSuccess(List<Dependency> list) {
        //1. Si esta visible NODATA se cambia visibilidad a GONE.
        if (llNoData.getVisibility() == View.VISIBLE)
            llNoData.setVisibility(View.GONE);
        //2. Se carga los datos en el Recycler.
        //rvDependency.setAdapter(adapter);
        adapter.update(list);

    }

}