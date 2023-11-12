package com.example.setik.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.setik.Adapter.TugasSelesaiAdapter
import com.example.setik.Adapter.TugasUserAdapter
import com.example.setik.MainActivity
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import com.example.setik.Retrofit.RetrofitTugasSelesai
import kotlinx.android.synthetic.main.activity_view_data_close.rvTugasUser_selesai
import kotlinx.android.synthetic.main.activity_view_data_tugas_user.rvTugasUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewDataClose : AppCompatActivity() {

    private val TAG2 : String = "ViewDataTugasUser"

    private val api by lazy { RetrofitTugasSelesai().endpoint }
    lateinit var tugasSelesaiAdapter: TugasSelesaiAdapter
    private lateinit var listDataUser : RecyclerView
    var listTugas = ArrayList<TugasUserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data_close)

        val kembali = findViewById<ImageView>(R.id.kembali_data_user)
        kembali.setOnClickListener {
            Intent(this@ViewDataClose , MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        getDataUser()
    }
    private fun getDataUser() {
        val id = getSharedPreferences("login_session", MODE_PRIVATE)
        val user = id.getString("id_user", "0")

        user?.let {
            api.TugasUserSelesai(
                it.toInt()
            )
                .enqueue(object : Callback<ArrayList<TugasUserModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<TugasUserModel>>,
                        response: Response<ArrayList<TugasUserModel>>
                    ) {
                        val responseCode = response.code().toString()
                        response.body()?.let { listTugas = it}
                        val adapter = TugasSelesaiAdapter(listTugas, object : TugasSelesaiAdapter.OnAdapterListener{
                            override fun onRead(tugas: TugasUserModel) {
                                startActivity(
                                    Intent(this@ViewDataClose, DetailTugasSelesai::class.java)
                                        .putExtra("tugas_user", tugas))
                            }
                        })
                        rvTugasUser_selesai.adapter = adapter
                    }
                    override fun onFailure(call: Call<ArrayList<TugasUserModel>>, t: Throwable) {
                    }
                })
        }
    }
}