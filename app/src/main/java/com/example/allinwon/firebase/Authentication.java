package com.example.allinwon.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    private static FirebaseAuth firebaseAuth;
    private static Authentication authentication;

    public static Authentication getInstance() {
        if(authentication == null) {
            authentication = new Authentication();
        }

        return authentication;
    }

    private Authentication() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signInFirebase(String email, String password, final FirebaseInterface firebaseInterface) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    firebaseInterface.onSuccess();
                } else {
                    firebaseInterface.onFailure();
                }
            }
        });
    }

    public void signUpFirebase(String email, String password, final FirebaseInterface firebaseInterface) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    firebaseInterface.onSuccess();
                } else {
                    firebaseInterface.onFailure();
                }
            }
        });
    }
}
