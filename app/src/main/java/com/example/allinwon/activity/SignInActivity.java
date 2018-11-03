package com.example.allinwon.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allinwon.firebase.Authentication;
import com.example.allinwon.firebase.AuthInterface;
import com.example.allinwon.R;

public class SignInActivity extends Activity implements View.OnClickListener {
    private EditText editText_email, editText_password;
    private ProgressDialog progressDialog;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if(Authentication.getInstance().isCurrentUser()) {
            Toast.makeText(getApplicationContext(), "로그인 되어있습니다.", Toast.LENGTH_LONG).show();
        }

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
                signUp();
                break;
            case R.id.login_signup:
        }
    }

    public void signUp() {
        email = editText_email.getText().toString();
        password = editText_password.getText().toString();

        if(!isValid()) {
            return;
        }

        progressDialog.setMessage("로그인 중...");
        progressDialog.dismiss();

        Authentication.getInstance().signIn(email, password, signInInterface);
    }

    AuthInterface signInInterface = new AuthInterface() {
        @Override
        public void onSuccess() {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
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
        } else if(TextUtils.isEmpty(password)) {
            editText_password.setError("비밀번호를 입력해주세요.");
            valid = false;
        }

        return valid;
    }
}