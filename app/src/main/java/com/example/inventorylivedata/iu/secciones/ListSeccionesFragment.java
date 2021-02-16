package com.example.inventorylivedata.iu.secciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Seccion;
import com.example.inventorylivedata.data.repository.SeccionesRepository;
import com.example.inventorylivedata.iu.adapter.SeccionesAdapter;
import com.example.inventorylivedata.iu.base.BaseDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListSeccionesFragment extends Fragment implements ListSeccionesContract.View {

    List<Seccion> list;
    ListSeccionesPresenter presenter;
    RecyclerView rvSecciones;
    SeccionesAdapter adapter;
    SeccionesAdapter.OnSeccionesListener listener;
    NavController navController;
    Seccion deleted;
    SeccionesRepository repository = new SeccionesRepository();
    FloatingActionButton fbSecciones;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_secciones, container, false);
        list = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSecciones = view.findViewById(R.id.rvSecciones);
        navController = Navigation.findNavController(view);
        fbSecciones = view.findViewById(R.id.fbSecciones);

        list = repository.get();

        fbSecciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ListSeccionesFragment.this).navigate(R.id.action_listSeccionesFragment_to_editSeccionesFragment2);
            }
        });

        listener = new SeccionesAdapter.OnSeccionesListener() {
            @Override
            public void onClick(View v) {
                Seccion seccion;
                //onEditDepedencyFragment(estanteria = adapter.getItem(rvSecciones.getChildAdapterPosition(v)));
            }

            @Override
            public boolean onLongClick(View v) {

                deleted = adapter.getItem(rvSecciones.getChildAdapterPosition(v));
                onDeleteSeccion(v);
                return true;
            }
        };


        //1. Crear el adapter
        adapter = new SeccionesAdapter(list, listener);

        //2. Crear el diseño del RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        //3. Se asigna el diseño al RecyclerView
        rvSecciones.setLayoutManager(layoutManager);

        //4. Vincular la vista al modelo
        rvSecciones.setAdapter(adapter);

        // Se inicializa el presenter
        presenter = new ListSeccionesPresenter(this);
    }

    private void onDeleteSeccion(View v) {
        Seccion seccion = adapter.getItem(rvSecciones.getChildAdapterPosition(v));
        Bundle bundle = new Bundle();
        bundle.putSerializable("seccion",seccion);
        bundle.putString(BaseDialogFragment.TITLE, getString(R.string.title_delete_seccion));
        bundle.putString(BaseDialogFragment.MESSAGE
                , String.format(getString(R.string.message_delete_seccion), seccion.getEstanteria()));

        NavHostFragment.findNavController(this).navigate(R.id.action_listSeccionesFragment_to_baseDialogFragment2, bundle);
    }

    @Override
    public void onStart() {
        super.onStart();


        presenter.load();

        if (getArguments() != null) {
            if (getArguments().getBoolean(BaseDialogFragment.CONFIRM_DELETE)) {
                deleted = (Seccion) getArguments().getSerializable("");
                repository.delete(deleted);
                showSnackBarDeleted();
            }
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setNoData() {

    }

    @Override
    public void onSuccessDeleted() {
        adapter.delete(deleted);
        showSnackBarDeleted();
    }

    private void showSnackBarDeleted() {
        Snackbar.make(getView(), getString(R.string.confirm_delete_seccion), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        undoDeleted();
                    }
                }).show();

    }

    private void undoDeleted() {
        presenter.undo(deleted);
    }


    @Override
    public void onSuccessUndo() {
        adapter.add(deleted);
    }

    @Override
    public void onSuccess(List<Seccion> list) {
        adapter.update(list);
    }
}
