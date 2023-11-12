package com.example.setik.Api

import com.example.setik.Model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiClient {

    @FormUrlEncoded
    @POST("setik/login/login_service.php")
    fun login(
        @Field("post_username") username : String,
        @Field("post_password") password : String
    ): Call<ResponseLogin>
}