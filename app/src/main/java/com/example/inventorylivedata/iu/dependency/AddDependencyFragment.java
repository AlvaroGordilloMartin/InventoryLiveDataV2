package com.example.inventorylivedata.iu.dependency;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.repository.DependencyRepository;
import com.example.inventorylivedata.iu.InventoryApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;


public class AddDependencyFragment extends Fragment {

    DependencyRepository repository = new DependencyRepository();

    TextInputEditText name,shortname,description,image;
    TextInputLayout tlname,tlshortname,tldescription,tlimage;
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dependency2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.btGuardarAdd);
        name = view.findViewById(R.id.tieNameAdd);
        tlname = view.findViewById(R.id.tilNameAdd);
        shortname = view.findViewById(R.id.tieShortNameAdd);
        tlshortname = view.findViewById(R.id.tilShortNameAdd);
        description = view.findViewById(R.id.tieDescriptionAdd);
        tldescription = view.findViewById(R.id.tilDescriptionAdd);
        image = view.findViewById(R.id.tieIrlImageAdd);
        tlimage = view.findViewById(R.id.tilIrlImageAdd);

        button.setOnClickListener(v -> Guardar(v));
    }

    private void Guardar(View v) {
        Dependency dependency = new Dependency(name.getText().toString(),shortname.getText().toString(),description.getText().toString(),image.getText().toString());
        repository.add(dependency);
        showUpNotification(dependency);
        Navigation.findNavController(v).navigateUp();
    }



    /**
     * Crea una notificacion cuando se crea una nueva dependencia
     */
    private void showUpNotification(Dependency dependency){
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
                .setDestination(R.id.editDependencyFragment)
                //.setArguments(bundle)
                .createPendingIntent();

        Notification.Builder builder = new Notification.Builder(getActivity(), InventoryApplication.CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getResources().getString(R.string.notification_title_dependency))
                .setContentText(getResources().getString(R.string.text_add_dependency))
                .setContentIntent(pendingIntent);

        //Se añade la notificacion al gestor de notificaciones
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(1000),builder.build());
    }


    private void showListFragment(){
        NavHostFragment.findNavController(this).navigateUp();
    }
}