package com.example.messi_lp.qiniuyun;



import com.example.messi_lp.qiniuyun.data.AddUrl;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiInterface {
    @POST("plat/")
    Observable<AddUrl> addUrl(@Body AddUrl addUrl);
}
