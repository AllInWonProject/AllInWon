package com.example.allinwon.firebase;

import android.support.annotation.NonNull;

import com.example.allinwon.firebase.Authentication;
import com.example.allinwon.firebase.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class User implements Serializable{
    private String email, name;

    public User() {
        email = Authentication.getInstance().getCurrentUser().getEmail();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }


}
