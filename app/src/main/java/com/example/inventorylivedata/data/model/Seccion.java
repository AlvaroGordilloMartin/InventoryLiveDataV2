package com.example.inventorylivedata.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity (foreignKeys = @ForeignKey(entity = Dependency.class,parentColumns = "shortname",childColumns = "shortname",onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE))
public class Seccion implements Serializable {

    @PrimaryKey
    @NonNull
    String estanteria;

    @NonNull
    String shortname;

    public Seccion(@NonNull String estanteria, @NonNull String shortname) {
        this.estanteria = estanteria;
        this.shortname = shortname;
    }


    public String getShortname() {
        return shortname;
    }

    public void setShortname(@NonNull String shortname) {
        this.shortname = shortname;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(String estanteria) {
        this.estanteria = estanteria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seccion that = (Seccion) o;
        return Objects.equals(estanteria, that.estanteria) &&
                Objects.equals(shortname, that.shortname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estanteria, shortname);
    }

    public void Edit(Seccion seccion) {
        this.estanteria = seccion.getEstanteria();
    }

    @Override
    public String toString() {
        return "Estanteria{" +
                "estanteria='" + estanteria + '\'' +
                ", dependencia=" + shortname +
                '}';
    }
}
