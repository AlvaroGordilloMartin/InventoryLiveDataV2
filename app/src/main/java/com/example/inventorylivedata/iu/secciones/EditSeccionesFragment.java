package com.example.inventorylivedata.iu.secciones;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.model.Seccion;
import com.example.inventorylivedata.data.repository.DependencyRepository;
import com.example.inventorylivedata.data.repository.SeccionesRepository;
import com.example.inventorylivedata.iu.InventoryApplication;

import java.util.List;
import java.util.Random;

public class EditSeccionesFragment extends Fragment {



    Spinner spinner;
    EditText editText;
    Button button;
    List<Dependency> list;
    DependencyRepository repository = new DependencyRepository();
    SeccionesRepository seccionesRepository = new SeccionesRepository();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_secciones, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.sp_secciones);
        editText = view.findViewById(R.id.edtSecciones);
        button = view.findViewById(R.id.btAddSecciones);
        list = repository.get();



        ArrayAdapter<Dependency> adapter = new ArrayAdapter<Dependency>(getContext(),  android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(v -> validateAdd());
    }

    private void validateAdd() {
        if(editText.toString().isEmpty())
            editText.setError("No puede estar vacio");
        else{
            seccionesRepository.add(new Seccion(editText.getText().toString(),((Dependency)spinner.getSelectedItem()).getShortname()));
            showUpNotification();
            NavHostFragment.findNavController(this).navigate(R.id.action_editSeccionesFragment_to_listSeccionesFragment);
        }

    }

    private void showUpNotification(){
        //Una PendingIntent tiene un objeto Intent en si interior que define lo que se quiere ejecutar cuando se pulse sobre la notificacion
        //Iniciar una activity
//        Intent intent = new Intent(getActivity(), SplashActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(Dependency.TAG,getDependency());
//        intent.putExtras(intent);
//
//        //Se construye el Pending Intent
//        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),new Random().nextInt(1000),intent,PendingIntent.FLAG_UPDATE_CURRENT);


        //Iniciar un fragment a traves del componente navigation
        //Se utiliza el grafico

        PendingIntent pendingIntent = new NavDeepLinkBuilder(getActivity())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.editSeccionesFragment)
                //.setArguments(bundle)
                .createPendingIntent();

        Notification.Builder builder = new Notification.Builder(getActivity(), InventoryApplication.CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getResources().getString(R.string.notification_title_seccion))
                .setContentText(getResources().getString(R.string.text_add_seccion))
                .setContentIntent(pendingIntent);

        //Se añade la notificacion al gestor de notificaciones
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(1000),builder.build());
    }


    private void showListFragment(){
        NavHostFragment.findNavController(this).navigateUp();
    }

}
