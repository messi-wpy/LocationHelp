package com.example.messi_lp.qiniuyun;

import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
                    HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client=new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .build();
                    Retrofit retrofit=new Retrofit.Builder()
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl("https://ccnubox.muxixyz.com/api/")
                            .build();
                    mApiService=retrofit.create(ApiInterface.class);
                }
            }

        }
        return mApiService;
    }
}
