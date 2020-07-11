package com.example.practicaldemo.api

import android.media.MediaMetadataRetriever
import com.example.practicaldemo.model.ApiResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("users")
    fun getList( @QueryMap params: HashMap<String,String>): Call<ApiResponseModel>

}