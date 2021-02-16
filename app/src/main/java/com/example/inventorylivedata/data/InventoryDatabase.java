package com.example.inventorylivedata.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.inventorylivedata.data.dao.DependencyDao;
import com.example.inventorylivedata.data.dao.SectionDao;
import com.example.inventorylivedata.data.model.Dependency;
import com.example.inventorylivedata.data.model.Seccion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Definir la configuracion de la base de datos
@Database(entities = {Dependency.class, Seccion.class},version = 2)
public abstract class InventoryDatabase extends RoomDatabase {

    //Crear los metodos de obtencion de los Dao
    public  abstract DependencyDao dependencyDao();
    public abstract SectionDao sectionDao();

    /**
     * Solucion Google: genera un problema ya que se necesita tener el contexto para obtener la BD
     */
    private static volatile InventoryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static InventoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (InventoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InventoryDatabase.class, "inventory")
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    /**
     * Otra opcion es separar la creacion de la obtencion
     */
    public static void create(final Context context){
        if (INSTANCE == null) {
            synchronized (InventoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InventoryDatabase.class, "inventory")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
    }

    public static InventoryDatabase getDatabase(){
        return INSTANCE;
    }
}


