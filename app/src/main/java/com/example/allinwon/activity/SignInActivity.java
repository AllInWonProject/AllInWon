package com.example.allinwon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.allinwon.authentication.Auth;
import com.example.allinwon.authentication.AuthInterface;
import com.example.allinwon.R;
import com.example.allinwon.database.Database;
import com.example.allinwon.database.DatabaseInterface;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignInActivity";

    private EditText editText_email, editText_password;
    private TextView textView_boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editText_email = findViewById(R.id.login_email);
        editText_password = findViewById(R.id.login_password);

        textView_boolean = findViewById(R.id.login_boolean);

        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.login_signup).setOnClickListener(this);

        autoLogin();
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

    public void autoLogin() {
        showProgress("자동 로그인 여부 확인 중,,,");
        Database.getInstance().setTextViewBoolean(Auth.getInstance().getCurrentUser().getEmail(), "자동로그인", textView_boolean, autoLoginInterface);
    }

    DatabaseInterface autoLoginInterface = new DatabaseInterface() {
        @Override
        public void onSuccess() {
            hideProgress();
            if(Auth.getInstance().getCurrentUser() != null && textView_boolean.getText().toString().equals("true")) {
                Log.d(TAG, "자동 로그인 성공");
                showToastMessage(getApplicationContext(), "자동 로그인 성공");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    };

    public void signIn() {
        if(!isValidForm()) {
            return;
        }

        showProgress("로그인 중...");
        Auth.getInstance().signInFirebase(editText_email.getText().toString(), editText_password.getText().toString(), signInInterface);
    }

    AuthInterface signInInterface = new AuthInterface() {
        @Override
        public void onSuccess() {
            hideProgress();
            Database.getInstance().setAutoLogin(editText_email.getText().toString());
            showToastMessage(getApplicationContext(), "로그인 성공");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            finish();
        }

        @Override
        public void onFailure() {
            hideProgress();
            showToastMessage(getApplicationContext(), "로그인 실패");
        }
    };

    public boolean isValidForm() {
        boolean valid = true;

        if(TextUtils.isEmpty(editText_email.getText().toString())) {
            editText_email.setError("이메일을 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(editText_password.getText().toString())) {
            editText_password.setError("비밀번호를 입력해주세요.");
            valid = false;
        }

        return valid;
    }
}