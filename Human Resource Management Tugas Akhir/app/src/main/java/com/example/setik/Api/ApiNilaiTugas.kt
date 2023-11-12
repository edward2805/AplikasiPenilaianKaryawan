package com.example.setik.Api

import com.example.setik.Model.TugasUserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNilaiTugas {


    @GET("setik/crud/tampil_nilai.php")
    fun NilaiTugas ( @Query("user_id") user_id: Int )
    : Call<ArrayList<TugasUserModel>>

}