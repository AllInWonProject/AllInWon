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

    public void signIn(String email, String password, final AuthInterface authInterface) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    authInterface.onSuccess();
                } else {
                    authInterface.onFailure();
                }
            }
        });
    }

    public void signUp(String email, String password, final AuthInterface authInterface) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    authInterface.onSuccess();
                } else {
                    authInterface.onFailure();
                }
            }
        });
    }

    public boolean isCurrentUser() {
        if(firebaseAuth.getCurrentUser() != null) {
            return true;
        } else {
            return false;
        }
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
