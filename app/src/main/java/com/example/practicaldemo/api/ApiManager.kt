package com.example.practicaldemo.api

import com.example.practicaldemo.AppConstant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class ApiManager {

    companion object {

        fun clientSSl(client: OkHttpClient): OkHttpClient {

            val builder = client.newBuilder()
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.hostnameVerifier(HostnameVerifier { s, sslSession ->
                true
            })
            return builder.build()
        }

        val instance: ApiInterface
            get() {
                val retrofit = ApiManager.retrofit
                return retrofit.create(ApiInterface::class.java)
            }

        val retrofit: Retrofit
            get() {
                val httpClient = OkHttpClient.Builder()
                httpClient.connectTimeout(2, TimeUnit.MINUTES)
                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logger)

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                return Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL)
                    .client(clientSSl(httpClient.build()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }

    }
}