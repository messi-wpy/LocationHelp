package com.example.messi_lp.qiniuyun;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateRetrofit {
    private static ApiInterface mApiService;
    private CreateRetrofit(){}
    public static ApiInterface getmApiService(){
        if (mApiService==null) {
            synchronized (CreateRetrofit.class){
                if (mApiService==null){
                    Retrofit retrofit=new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl("ccnubox.muxixyz.com/api")
                            .build();
                    mApiService=retrofit.create(ApiInterface.class);
                }
            }

        }
        return mApiService;
    }
}
