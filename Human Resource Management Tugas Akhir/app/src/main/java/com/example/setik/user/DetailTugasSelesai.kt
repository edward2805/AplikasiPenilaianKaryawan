package com.example.setik.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import com.example.setik.Retrofit.RetrofitTugasSelesai
import kotlinx.android.synthetic.main.activity_detail_tugas_selesai.*

class DetailTugasSelesai : AppCompatActivity() {

    private val api by lazy { RetrofitTugasSelesai().endpoint }

    private val dataTugas by lazy { intent.getSerializableExtra("tugas_user") as TugasUserModel }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas_selesai)

        setupView()

        val kembali = findViewById<ImageView>(R.id.kembali_edit_tugas_user)
        kembali.setOnClickListener {
            Intent(this@DetailTugasSelesai , ViewDataClose::class.java).also {
                startActivity(it)
            }
        }
    }
    private fun setupView() {
        tv_det_id_tugas_user.setText(dataTugas.id_tugas)
        tv_det_nama_user.setText(dataTugas.nama)
        tv_det_tt_number_user.setText(dataTugas.tt_number)
        tv_det_site_id_user.setText(dataTugas.site_id)
        tv_det_site_name_user.setText(dataTugas.site_name)
        tv_det_tenant_user.setText(dataTugas.tenant_name)
        tv_det_alamat_user.setText(dataTugas.alamat)
        tv_det_tipe_user.setText(dataTugas.tipe)
        tv_det_status_user.setText(dataTugas.status)
        tv_det_start_tugas.setText(dataTugas.start_tugas)
        tv_det_end_tugas.setText(dataTugas.end_tugas)
        tv_date_selesai.setText(dataTugas.tugas_selesai)
        tv_det_durasi_selesai.setText(dataTugas.durasi_selesai)
    }
}