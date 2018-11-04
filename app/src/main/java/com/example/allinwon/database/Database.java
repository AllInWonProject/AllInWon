package com.example.allinwon.database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final String TAG = "Database";

    private static String USER = "사용자";

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

    public void createNewUser(String email, String name) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("이름", name);
        userInfo.put("자동로그인", false);

        firebaseFirestore.collection(USER).document(email).set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "새로운 유저 정보 등록 완료");
                }
            }
        });
    }

    public void setAutoLogin(String email) {
        Map<String, Object> autoLogin = new HashMap<>();
        autoLogin.put("자동로그인", true);

        firebaseFirestore.collection(USER).document(email).update(autoLogin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "자동로그인을 true로 설정");
                }
            }
        });
    }

    public void setTextViewToString(String email, final String data, final TextView textView, final DatabaseInterface databaseInterface) {
        firebaseFirestore.collection(USER).document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "DB에서 정보 가져오기 성공");
                    textView.setText(task.getResult().getString(data));
                    databaseInterface.onSuccess();
                }
            }
        });
    }

    public void setTextViewBoolean(String email, final String data, final TextView textView, final DatabaseInterface databaseInterface) {
        firebaseFirestore.collection(USER).document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "DB에서 정보 가져오기 성공");
                    textView.setText(String.valueOf(task.getResult().getBoolean(data)));
                    databaseInterface.onSuccess();
                }
            }
        });
    }
}
