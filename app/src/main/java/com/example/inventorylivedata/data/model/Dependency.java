package com.example.inventorylivedata.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Dependency implements Serializable, Parcelable,Comparable {
    public static final String TAG = "Dependency";

    @NonNull
    private String name;
    @PrimaryKey
    @NonNull
    private String shortname;
    @NonNull
    private String description;
    private String urlImage;

    public Dependency(String name, String shortname, String description, String urlImage) {
        this.name = name;
        this.shortname = shortname;
        this.description = description;
        this.urlImage = urlImage;
    }

    @Ignore
    public Dependency() {

    }

    @Ignore
    protected Dependency(Parcel in) {
        name = in.readString();
        shortname = in.readString();
        description = in.readString();
        urlImage = in.readString();
    }

    public static final Creator<Dependency> CREATOR = new Creator<Dependency>() {
        @Override
        public Dependency createFromParcel(Parcel in) {
            return new Dependency(in);
        }

        @Override
        public Dependency[] newArray(int size) {
            return new Dependency[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return shortname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        return shortname.equals(that.shortname);
    }

    @Override
    public int hashCode() {
        return shortname.hashCode();
    }

    public void Edit(Dependency dependency) {
        this.name = dependency.name;
        this.description = dependency.description;
        this.urlImage = dependency.urlImage;
        this.shortname = dependency.shortname;
    }

    @Override
    public int compareTo(Object o) {
        return getShortname().compareTo(((Dependency)o).getShortname());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shortname);
        dest.writeString(description);
        dest.writeString(urlImage);
    }
}
