package com.example.allinwon.activity;

import android.os.Bundle;

import com.example.allinwon.R;
import com.example.allinwon.currency.Currency;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Currency.getInstance().getCurrency();
        Currency.getInstance().getCurrencyArray();
    }
}
