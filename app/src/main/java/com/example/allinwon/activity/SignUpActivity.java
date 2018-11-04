package com.example.allinwon.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allinwon.R;
import com.example.allinwon.firebase.Database;
import com.example.allinwon.firebase.FirebaseInterface;
import com.example.allinwon.firebase.Authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends Activity implements View.OnClickListener {
    private EditText editText_name, editText_email, editText_password1, editText_password2;
    private ProgressDialog progressDialog;
    private String email, name, password1, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editText_email = findViewById(R.id.signup_email);
        editText_name = findViewById(R.id.signup_name);
        editText_password1 = findViewById(R.id.signup_password1);
        editText_password2 = findViewById(R.id.signup_password2);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.signup_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.signup_button:
                signUp();
                break;
        }
    }

    public void signUp() {
        email = editText_email.getText().toString();
        name = editText_name.getText().toString();
        password1 = editText_password1.getText().toString();
        password2 = editText_password2.getText().toString();

        if(!isValid()) {
            return;
        }

        progressDialog.setMessage("회원 가입 중...");
        progressDialog.show();

        Authentication.getInstance().signUpFirebase(email, password1, signUpInterface);
    }

    FirebaseInterface signUpInterface = new FirebaseInterface() {
        @Override
        public void onSuccess() {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "회원 가입 완료", Toast.LENGTH_LONG).show();
            Database.getInstance().createUser(name, email);
            finish();
            overridePendingTransition(R.anim.not_move_activity, R.anim.right_out_activity);
        }

        @Override
        public void onFailure() {
            progressDialog.dismiss();
            editText_email.setError("이미 가입되어있는 이메일입니다.");
        }
    };

    public boolean isValid() {
        Pattern emailPattern = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
        Matcher matcher = emailPattern.matcher(email);

        boolean valid = true;

        if(TextUtils.isEmpty(email)) {
            editText_email.setError("이메일을 입력해주세요.");
            valid = false;
        } if(!matcher.matches()) {
            editText_email.setError("유효한 이메일을 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(name)) {
            editText_name.setError("이름을 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(password1)) {
            editText_password1.setError("비밀번호를 입력해주세요.");
            valid = false;
        } if(password1.length() < 6) {
            editText_password1.setError("비밀번호는 6자리 이상 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(password2)) {
            editText_password2.setError("비밀번호를 재입력해주세요.");
            valid = false;
        } if(!password1.equals(password2)) {
            editText_password2.setError("비밀번호가 일치하지 않습니다.");
            valid = false;
        }

        return valid;
    }
}
