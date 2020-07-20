package com.hh1995.life;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    public static Retrofit getInstance(){
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://hkh26.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        return  builder.build();
    }

    public static Retrofit getInstance2(){
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://hkh26.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        return  builder.build();
    }
}
