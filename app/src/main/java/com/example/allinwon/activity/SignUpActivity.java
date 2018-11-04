package com.example.allinwon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.allinwon.R;
import com.example.allinwon.authentication.AuthInterface;
import com.example.allinwon.authentication.Auth;
import com.example.allinwon.database.Database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private EditText editText_name, editText_email, editText_password1, editText_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editText_email = findViewById(R.id.signup_email);
        editText_name = findViewById(R.id.signup_name);
        editText_password1 = findViewById(R.id.signup_password1);
        editText_password2 = findViewById(R.id.signup_password2);

        findViewById(R.id.signup_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        signUp();
    }

    public void signUp() {
        if(!isValidForm()) {
            return;
        }

        showProgress("회원 가입 중...");
        Auth.getInstance().signUpFirebase(editText_email.getText().toString(), editText_password1.getText().toString(), signUpInterface);
    }

    AuthInterface signUpInterface = new AuthInterface() {
        @Override
        public void onSuccess() {
            hideProgress();
            Database.getInstance().createNewUser(editText_email.getText().toString(), editText_name.getText().toString());
            showToastMessage(getApplicationContext(), "회원 가입 성공");
            finish();
            overridePendingTransition(R.anim.not_move_activity, R.anim.right_out_activity);
        }

        @Override
        public void onFailure() {
            hideProgress();
            editText_email.setError("이미 가입되어있는 이메일입니다.");
        }
    };

    public boolean isValidForm() {
        Pattern emailPattern = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
        Matcher matcher = emailPattern.matcher(editText_email.getText().toString());

        boolean valid = true;

        if(TextUtils.isEmpty(editText_email.getText().toString())) {
            editText_email.setError("이메일을 입력해주세요.");
            valid = false;
        } if(!matcher.matches()) {
            editText_email.setError("유효한 이메일을 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(editText_name.getText().toString())) {
            editText_name.setError("이름을 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(editText_password1.getText().toString())) {
            editText_password1.setError("비밀번호를 입력해주세요.");
            valid = false;
        } if(editText_password1.getText().toString().length() < 6) {
            editText_password1.setError("비밀번호는 6자리 이상 입력해주세요.");
            valid = false;
        } if(TextUtils.isEmpty(editText_password2.getText().toString())) {
            editText_password2.setError("비밀번호를 재입력해주세요.");
            valid = false;
        } if(!editText_password1.getText().toString().equals(editText_password2.getText().toString())) {
            editText_password2.setError("비밀번호가 일치하지 않습니다.");
            valid = false;
        }

        return valid;
    }
}
