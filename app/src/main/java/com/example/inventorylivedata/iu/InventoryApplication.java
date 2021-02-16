package com.example.inventorylivedata.iu;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;


import com.example.inventorylivedata.R;
import com.example.inventorylivedata.data.InventoryDatabase;
import com.example.inventorylivedata.iu.preferences.InventoryPreferences;

public class InventoryApplication extends Application {

    public static final String CHANNEL_ID ="123";

    @Override
    public void onCreate() {
        super.onCreate();
        InventoryDatabase.create(this);
        InventoryPreferences.newInstance(this);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        //Vamos a crear la clase NotificationChannel pero solo para API 26+ porque esta clase NotificationChannel no esta en la libreria de soporte
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            int importace = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importace);

            //Se registra el canal en el sistema y una vez se ha registrado no se puede cambiar la importancia o cualquier otra configuracion que se haya establecido
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
