package com.example.allinwon.currency;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyInterface {
    String baseUrl = "http://api.manana.kr/";

    @GET("exchange/rate/KRW/USD,EUR,JPY,CNY.json")
    Call<List<CurrencyInfo>> getInfo();
}
