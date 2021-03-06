package com.example.inventorylivedata.iu.dependency;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.repository.DependencyRepository;
import com.example.inventorylivedata.iu.adapter.DependencyAdapter;
import com.example.inventorylivedata.iu.base.BaseDialogFragment;
import com.example.inventorylivedata.iu.base.BaseView;
import com.example.inventorylivedata.iu.base.BaseViewModelDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListDependencyFragment extends Fragment {

    private LinearLayout llLoading;
    private LinearLayout llNoData;
    private RecyclerView rvDependency;
    private DependencyAdapter adapter;
    private List<Dependency> list;
    DependencyRepository repository = new DependencyRepository();
    private DependencyAdapter.OnDepedencyClickListener listener;
    private Bundle bundle;
    private NavController navController;
    private Dependency deleted;
    FloatingActionButton button;
    ListDependencyViewModel viewModel;
    BaseViewModelDialog modelDialog;
    SwipeRefreshLayout swiperefresh;

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
        viewModel = ViewModelProviders.of(this).get(ListDependencyViewModel.class);
        initUI(view);
        initRecycler();
        //Finalmente solicitamos los datos
        getDependency();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDependency();
            }
        });

        button.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_listDependencyFragment_to_addDependencyFragment));

        navController = Navigation.findNavController(view);



        // Se inicializa el presenter
    }

    private void getDependency() {
        showProgress();
        //Observamos el estado o el cambio del ViewModel
        viewModel.getDependencies().observe(getViewLifecycleOwner(), dependencies -> {
            adapter.update(dependencies);
            swiperefresh.setRefreshing(false);
            hideProgress();
        });
    }


    /**
     * Metodo que inicializa las vistas
     *
     * @param view
     */
    private void initUI(View view) {
        llLoading = view.findViewById(R.id.llLoading);
        llNoData = view.findViewById(R.id.llNoData);
        button = view.findViewById(R.id.fab);
        rvDependency = view.findViewById(R.id.rvDependency);
        swiperefresh = view.findViewById(R.id.swipereferesh);

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

    }

    /**
     * Metodo que incializa el recyclerView
     */
    private void initRecycler() {

        //list = repository.get();

        //1. Crear el adapter
        adapter = new DependencyAdapter(list, listener);

        //2. Crear el diseño del RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        //3. Se asigna el diseño al RecyclerView
        rvDependency.setLayoutManager(layoutManager);

        //4. Vincular la vista al modelo
        rvDependency.setAdapter(adapter);
    }

    public void onEditDepedencyFragment(Dependency dependency) {
        bundle = new Bundle();
        bundle.putSerializable(dependency.TAG, dependency);

        NavHostFragment.findNavController(this).navigate(R.id.editDependencyFragment, bundle);
    }

    public void onDeleteDependency(View v) {
        //Inicializo el viewmodel dentro del ciclo de vida de la activity, cualquier otro fragment que instancie esa clase obtiene la misma instancia
        modelDialog = ViewModelProviders.of(requireActivity()).get(BaseViewModelDialog.class);
        Dependency dependency = adapter.getItem(rvDependency.getChildAdapterPosition(v));
        Bundle bundle = new Bundle();
        bundle.putSerializable("dependencia", dependency);
        bundle.putString(BaseDialogFragment.TITLE, getString(R.string.title_delete_dependency));
        bundle.putString(BaseDialogFragment.MESSAGE
                , String.format(getString(R.string.message_delete_dependency), dependency.getShortname()));

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_listDependencyFragment_to_baseDialogFragment, bundle);

        modelDialog.getConfirm().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.delete(dependency);

                }
            }
        });
    }

    /**
     * Método que muestras el LineasLayout que contiene el progressbar
     */
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    public void setNoData() {
        llNoData.setVisibility(View.VISIBLE);
    }

    /**
     * Este método es el que se ejecuta cuando se elimina correctamente una dependencia de la bd
     * y muestra un snackbar con la opción Undo
     */
    public void onSuccessDeleted() {
        adapter.delete(deleted);
        showSnackBarDeleted();
    }

    private void showSnackBarDeleted() {
        Snackbar.make(getView(), getString(R.string.confirm_delete_dependency), Snackbar.LENGTH_SHORT)
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
    }


    /**
     * Método que inserta una dependencia previamente eliminada
     */

    public void onSuccessUndo() {
        adapter.add(deleted);
    }


    public void onSuccess(List<Dependency> list) {
        //1. Si esta visible NODATA se cambia visibilidad a GONE.
        if (llNoData.getVisibility() == View.VISIBLE)
            llNoData.setVisibility(View.GONE);
        //2. Se carga los datos en el Recycler.
        //rvDependency.setAdapter(adapter);
        adapter.update(list);

    }

}