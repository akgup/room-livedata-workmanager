package com.buzzybrains.mvvmarchitecture.retrofit;

import com.buzzybrains.mvvmarchitecture.model.Note;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RemoteNoteEndpoint {

    @Headers("Content-Type: application/json")
    @POST("/fa4888ac-ad91-4d08-b3fd-b05ffe306e1e")
    Call<ResponseBody> addNote(@Body Note note);

}
