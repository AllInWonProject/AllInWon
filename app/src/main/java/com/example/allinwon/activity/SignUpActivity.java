package com.example.allinwon.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.allinwon.R;

public class SignUpActivity extends Activity implements View.OnClickListener {
    private EditText editTextName, editTextEmamil, editTextPassword1, editTextpassword2;
    private ProgressDialog progressDialog;
    private String email, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void onClick(View v) {

    }
}
