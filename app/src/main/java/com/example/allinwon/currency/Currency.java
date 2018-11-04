package com.example.allinwon.currency;

import android.icu.util.LocaleData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Currency {
    private static final String TAG = "Currency";

    private static Retrofit retrofit;
    private static CurrencyInterface currencyInterface;
    private static Currency currency;
    private static List<CurrencyInfo> array;

    public static Currency getInstance() {
        if(currency == null) {
            currency = new Currency();
        }
        
        return currency;
    }

    private Currency() {
        array = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(CurrencyInterface.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        currencyInterface = retrofit.create(CurrencyInterface.class);
    }

    public void getCurrency() {
        Call<List<CurrencyInfo>> call = currencyInterface.getInfo();

        call.enqueue(new Callback<List<CurrencyInfo>>() {
            @Override
            public void onResponse(Call<List<CurrencyInfo>> call, Response<List<CurrencyInfo>> response) {
                if(response.isSuccessful()) {
                    if(!response.body().isEmpty()) {
                        Log.d(TAG, "환율 API 정보 불러오기 성공");
                        for(int i = 0; i < response.body().size(); i++) {
                            array.add(response.body().get(i));
                        }
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<CurrencyInfo>> call, Throwable t) {
                Log.d(TAG, "환율 정보 불러오기 실패");
            }
        });
    }

    public List<CurrencyInfo> getCurrencyArray() {
        return array;
    }
}
