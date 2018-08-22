package com.example.messi_lp.qiniuyun;



import com.example.messi_lp.qiniuyun.data.AddUrl;
import com.example.messi_lp.qiniuyun.data.Delate;
import com.example.messi_lp.qiniuyun.data.Detail;
import com.example.messi_lp.qiniuyun.data.LocationName;
import com.example.messi_lp.qiniuyun.data.Msg;
import com.example.messi_lp.qiniuyun.data.Update;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {
    @POST("plat/")
    Observable<AddUrl> addUrl(@Body AddUrl addUrl);

    @GET("plat/names/")
    Observable<LocationName>getNames();

    @GET("plat/detail/")
    Observable<Detail>getDetail(@Query("name")String name);

    @PUT("plat/")
    Observable<Msg>updata(@Body Update update);

    @HTTP(method = "DELETE", path = "plat/", hasBody = true)
    Observable<Msg>delete(@Body Delate delate);
}
