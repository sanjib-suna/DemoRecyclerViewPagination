package com.example.practicaldemo.api

import android.hardware.camera2.CaptureFailure
import okhttp3.ResponseBody
import okhttp3.internal.http2.Header
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <T> Call<T>.callApi(onSuccess:(body:T) -> Unit,onFailure: (t:Throwable?) -> Unit){
    this.enqueue(object :Callback<T>{
        override fun onResponse(call: Call<T>, response: Response<T>) {

            val body: T ? = response.body()
            val errorbody:ResponseBody? = response.errorBody()

            if(body!= null){
                onSuccess(body)
            }else{
                onFailure(Throwable("Something Went wrong"))
            }

        }
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }
    })
}