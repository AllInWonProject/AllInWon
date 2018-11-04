package com.example.allinwon;

import com.example.allinwon.authentication.Auth;

public class User {
    private String email;
    private String name;

    public User() {
        email = Auth.getInstance().getCurrentUser().getEmail();
        name = null;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
