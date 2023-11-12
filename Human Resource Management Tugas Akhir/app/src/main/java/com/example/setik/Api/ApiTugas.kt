package com.example.setik.Api

import com.example.setik.Model.TugasUserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiTugas {

    @GET("setik/crud/tampil_data_user.php")
    fun TugasUser ( @Query ("user_id") user_id: Int )
    : Call<ArrayList<TugasUserModel>>


    @Multipart
    @POST("setik/crud/edit_tgs.php")
    fun update(
        @Part gambar: MultipartBody.Part,
        @Part ("id_tugas") id_tugas: RequestBody,
        @Part ("status") status: RequestBody,
        @Part ("keterangan") keterangan: RequestBody,
        ) : Call<TugasUserModel>

}