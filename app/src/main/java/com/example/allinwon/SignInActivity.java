package com.example.allinwon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends Activity implements View.OnClickListener
{
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private EditText editText_email, editText_password;
    private ProgressDialog progressDialog;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) { user = firebaseAuth.getCurrentUser(); }
        if(user != null) { }

        editText_email = findViewById(R.id.login_email);
        editText_password = findViewById(R.id.login_password);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.login_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.login_button:
                signUp();
                break;
            case R.id.login_signup:
        }
    }

    public void signUp()
    {
        email = editText_email.getText().toString();
        password = editText_password.getText().toString();

        if(!isValid())
        {
            return;
        }

        setProgressDialog("로그인 중...");

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                setProgressDialog("quit");
                if(task.isSuccessful())
                {
                    user = firebaseAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setProgressDialog(String message) {
        if(message.equals("quit"))
        {
            progressDialog.dismiss();
        }
        else
        {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public boolean isValid()
    {
        boolean valid = true;

        if(TextUtils.isEmpty(email))
        {
            editText_email.setError("이메일을 입력해주세요.");
            valid = false;
        } else if(TextUtils.isEmpty(password))
        {
            editText_password.setError("비밀번호를 입력해주세요.");
            valid = false;
        }

        return valid;
    }
}
