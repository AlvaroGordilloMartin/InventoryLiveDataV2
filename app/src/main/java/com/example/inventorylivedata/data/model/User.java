package com.example.inventorylivedata.data.model;

import androidx.annotation.NonNull;

public class User {
    private int id;
    private String user;
    private String email;
    private String password;

    private String name;
    private String imgURL;
    private int typeUser;

    public User() {
    }

    public User(String user, String password){
        this.user=user;
        this.password=password;
    }

    public User(int id, String user, String password, String name, String email, String imgURL, int typeUser) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.name = name;
        this.email = email;
        this.imgURL = imgURL;
        this.typeUser = typeUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "user='" + user + '\'' +
                "email='" + email + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return user.equals(user.user);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        return result;
    }
}
