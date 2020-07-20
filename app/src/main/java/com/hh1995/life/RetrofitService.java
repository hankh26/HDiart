package com.hh1995.life;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitService {
    @Multipart
    @POST("/Diary/insertDB.php")
    Call<String> postDataToDiary(@PartMap Map<String,String> datePart,
                                 @Part MultipartBody.Part filePart);

    @GET("/Diary/loadDB.php")
    Call<ArrayList<DiaryItem>> loadDataFromDiary();

}
