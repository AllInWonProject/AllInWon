package com.example.allinwon.firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.allinwon.activity.MainActivity;
import com.example.allinwon.activity.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Database {
    public static final String USER = "사용자";
    public static final String NAME = "이름";
    public static final String AUTO_LOGIN = "자동로그인";

    private static FirebaseFirestore firebaseFirestore;
    private static Database database;

    public static Database getInstance() {
        if(database == null) {
            database = new Database();
        }

        return database;
    }

    private Database() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public void createUser(String name, String email) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(NAME, name);

        firebaseFirestore.collection(USER).document(email).set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                        }
                    }
                });
    }


    public void setAutoLogin(String email, String type) {
        Map<String, Object> autoLogin = new HashMap<>();

        if(type.equals("on")) {
            autoLogin.put(AUTO_LOGIN, true);
        } else if(type.equals("off")) {
            autoLogin.put(AUTO_LOGIN, false);
        }

        firebaseFirestore.collection(USER).document(email).update(autoLogin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                }
            }
        });
    }
}
