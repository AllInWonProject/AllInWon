package com.example.allinwon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity implements View.OnClickListener
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
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) { user = firebaseAuth.getCurrentUser(); }
        if(user != null) { }

        editText_email = findViewById(R.id.login_email);
        editText_password = findViewById(R.id.login_password);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.login_signup).setOnClickListener(this);
    }

    public void test() {
        
    }

}
