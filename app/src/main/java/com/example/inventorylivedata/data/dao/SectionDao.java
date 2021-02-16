package com.example.inventorylivedata.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorylivedata.data.model.Seccion;

import java.util.List;

@Dao
public interface SectionDao {
    //Si hay un error el id que se devuelve  es -1
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long insert (Seccion section);

    @Delete
    void delete(Seccion section);

    @Update
    void update(Seccion section);

    //Vamos a personalizar varias sentencias SELECT
    @Query("SELECT * from Seccion ORDER BY estanteria")
    List<Seccion> get();

    @Query("SELECT * from Seccion WHERE estanteria=:shortname")
    Seccion findByShortname(String shortname);

    @Query("SELECT COUNT(*) FROM dependency")
    int getRowCount();
}
