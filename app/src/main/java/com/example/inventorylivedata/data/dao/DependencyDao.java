package com.example.inventorylivedata.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorylivedata.data.model.Dependency;

import java.util.List;

@Dao
public interface DependencyDao {
    //Si hay un error el id que se devuelve  es -1
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long insert (Dependency dependency);

    @Delete
    void delete(Dependency dependency);

    @Update
    void update(Dependency dependency);

    //Vamos a personalizar varias sentencias SELECT
    @Query("SELECT * from dependency ORDER BY shortname")
    List<Dependency> get();

    @Query("SELECT * from dependency WHERE shortname=:shortname")
    Dependency findByShortname(String shortname);

    @Query("SELECT COUNT(*) FROM dependency")
    int getRowCount();
}
