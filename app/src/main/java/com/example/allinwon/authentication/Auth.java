package com.example.allinwon.authentication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth {
    private  static final String TAG = "Auth";

    private static FirebaseAuth firebaseAuth;
    private static Auth auth;

    public static Auth getInstance() {
        if(auth == null) {
            auth = new Auth();
        }

        return auth;
    }

    private Auth() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signInFirebase(String email, String password, final AuthInterface authInterface) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "로그인 성공");
                    authInterface.onSuccess();
                } else {
                    authInterface.onFailure();
                }
            }
        });
    }

    public void signUpFirebase(String email, String password, final AuthInterface authInterface) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "회원가입 성공");
                    authInterface.onSuccess();
                } else {
                    authInterface.onFailure();
                }
            }
        });
    }
}
