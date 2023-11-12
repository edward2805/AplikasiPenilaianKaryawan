package com.example.setik.Retrofit

import com.example.setik.Api.ApiTugas
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTugasUser {

    val endpoint: ApiTugas
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://sinarpalasariapp.my.id/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit.create(ApiTugas::class.java)
        }
}