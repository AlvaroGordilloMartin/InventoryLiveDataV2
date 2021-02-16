package com.example.inventorylivedata.iu.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.model.Seccion;
import com.example.inventorylivedata.data.repository.DependencyRepository;
import com.example.inventorylivedata.data.repository.SeccionesRepository;

public class BaseDialogFragment extends DialogFragment {
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String CONFIRM_DELETE = "confirm delete";
    public static final String DELETED = "deleted";

    DependencyRepository repository = new DependencyRepository();
    SeccionesRepository seccionesRepository = new SeccionesRepository();

/*
No funciona con el componenete navigation si se puede usar con el fragment manager
    public interface onPositiveClickListener{
        void onPositiveClick();
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments()!=null) {
            String title=getArguments().getString(TITLE);
            String message = getArguments().getString(MESSAGE);
            if(title.equals(getString(R.string.title_delete_seccion))){
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ESTO ESTA FEISIMO
                        seccionesRepository.delete((Seccion)getArguments().getSerializable("seccion"));
                        NavHostFragment.findNavController(BaseDialogFragment.this)
                                .navigate(R.id.listSeccionesFragment);


                        //NO FUNCIONA CON NAVIGATION COMPONENT, da error porque getTargetFragment es null
                        //((onPositiveClickListener)getTargetFragment()).onPositiveClick();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                return builder.create();
            }
            if(title.equals(getString(R.string.title_delete_dependency))) {
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ESTO ESTA FEISIMO
                        repository.delete((Dependency) getArguments().getSerializable("dependencia"));
                        NavHostFragment.findNavController(BaseDialogFragment.this)
                                .navigate(R.id.listDependencyFragment);


                        //NO FUNCIONA CON NAVIGATION COMPONENT, da error porque getTargetFragment es null
                        //((onPositiveClickListener)getTargetFragment()).onPositiveClick();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                return builder.create();
            }
            else
                return builder.create();
        }
        return null;
    }
}
