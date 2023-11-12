package com.example.setik.Model

import java.io.Serializable


data class TugasUserModel (
    val id_tugas: String?,
    val user_id: String?,
    val nama: String?,
    val tt_number: String?,
    val site_id: String?,
    val site_name: String?,
    val tenant_name: String?,
    val status: String?,
    val alamat:String?,
    val tipe: String?,
    val start_tugas: String?,
    val end_tugas: String?,
    val tugas_selesai: String?,
    val durasi_selesai: String?,
    val total_durasi: String?,
    val keterangan: String?,
    val path_gambar: String?,
    val gambar: String?,
    val id_penilaian: String?,
    val nilai: String?,
    val durasi_tugas : String?,
    val total_nilai : String?,
    val total_tugas : String?,
    val rata_rata_tugas : String?

    ) : Serializable