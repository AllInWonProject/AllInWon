package com.example.allinwon.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allinwon.firebase.User;
import com.example.allinwon.firebase.Authentication;
import com.example.allinwon.firebase.Database;
import com.example.allinwon.firebase.FirebaseInterface;
import com.example.allinwon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends Activity implements View.OnClickListener {
    private EditText editText_email, editText_password;
    private ProgressDialog progressDialog;
    private String email, password;
    private User currentUser;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editText_email = findViewById(R.id.login_email);
        editText_password = findViewById(R.id.login_password);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.login_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_button:
                signIn();
                break;
            case R.id.login_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
                break;
        }
    }

    public void signIn() {
        email = editText_email.getText().toString();
        password = editText_password.getText().toString();

        if(!isValid()) {
            return;
        }

        progressDialog.setMessage("로그인 중...");
        progressDialog.show();

        Authentication.getInstance().signInFirebase(email, password, signInInterface);
    }

    FirebaseInterface signInInterface = new FirebaseInterface() {
        @Override
        public void onSuccess() {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
            currentUser = new User();
            Database.getInstance().setAutoLogin(email, "on");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userInfo", currentUser);
            startActivity(intent);
        }

        @Override
        public void onFailure() {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
        }
    };

    public boolean isValid() {
        boolean valid = true;

        if(TextUtils.isEmpty(email)) {
            editText_email.setError("이메일을 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(password)) {
            editText_password.setError("비밀번호를 입력해주세요.");
            valid = false;
        }

        return valid;
    }
}