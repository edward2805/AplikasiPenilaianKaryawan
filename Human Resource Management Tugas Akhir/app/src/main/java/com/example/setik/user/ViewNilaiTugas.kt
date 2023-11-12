package com.example.setik.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.setik.Adapter.NilaiTugasAdapter
import com.example.setik.Adapter.TotalNilaiAdapter
import com.example.setik.MainActivity
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import com.example.setik.Retrofit.RetrofitNilaiTugas
import com.example.setik.Retrofit.RetrofitTotalNilai
import kotlinx.android.synthetic.main.activity_view_nilai_tugas.rv_total_nilai_tugas
import kotlinx.android.synthetic.main.activity_view_nilai_tugas.rvnilai_tugas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewNilaiTugas : AppCompatActivity() {

    private val TAG2 : String = "ViewNilaiTugas"
    private val TAG3 : String = "ViewNilaiTugas"

    private val api by lazy { RetrofitNilaiTugas().endpoint }
    private val api1 by lazy { RetrofitTotalNilai().endpoint }

    lateinit var nilaiTugasAdapter: NilaiTugasAdapter
    lateinit var totalNilaiAdapter: TotalNilaiAdapter

    private lateinit var listtotalNilai : RecyclerView
    private lateinit var listNilaiTugas : RecyclerView

    var listNilai = ArrayList<TugasUserModel>()
    var listNilaiTotal = ArrayList<TugasUserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_nilai_tugas)

        val kembali = findViewById<ImageView>(R.id.kembali_nilai_tugas)
        kembali.setOnClickListener{
            Intent(this@ViewNilaiTugas, MainActivity::class.java).also {
                startActivity(it)
            }
        }

    }
    override fun onStart(){
        super.onStart()
        getDataNilai()
        getDataTotalNilai()
    }


    private fun getDataTotalNilai(){
        val id = getSharedPreferences("login_session", MODE_PRIVATE)
        val user = id.getString("id_user", "0")

        user?.let {
            api1.TotalNilai(
                it.toInt()
            )
                .enqueue(object : Callback<ArrayList<TugasUserModel>>{
                    override fun onResponse(
                        call: Call<ArrayList<TugasUserModel>>,
                        response: Response<ArrayList<TugasUserModel>>
                    ) {
                        val responseCode = response.code().toString()
                        response.body()?.let { listNilaiTotal = it } // Perbarui listNilaiTotal
                        val adapter = TotalNilaiAdapter(listNilaiTotal, object : TotalNilaiAdapter.OnAdapterListener{
                            override fun onReadNilaiTotal(totalNilaiUser: TugasUserModel) {

                            }
                        })
                        val recyclerView2 = findViewById<RecyclerView>(R.id.rv_total_nilai_tugas)
                        recyclerView2.adapter = adapter
                    }
                    override fun onFailure(call: Call<ArrayList<TugasUserModel>>, t: Throwable) {
                    }
                })
        }
    }

    private fun getDataNilai(){
        val id = getSharedPreferences("login_session", MODE_PRIVATE)
        val user = id.getString("id_user", "0")

        user?.let {
            api.NilaiTugas(
                it.toInt()
            )
                .enqueue(object : Callback<ArrayList<TugasUserModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<TugasUserModel>>,
                        response: Response<ArrayList<TugasUserModel>>
                    ){
                        val responseCode = response.code().toString()
                        response.body()?.let { listNilai = it }
                        val adapter = NilaiTugasAdapter(listNilai, object : NilaiTugasAdapter.OnAdapterListener{
                            override fun onRead(nilai: TugasUserModel) {
                                startActivity(
                                    Intent(this@ViewNilaiTugas, DetailNilaiTugas::class.java)
                                        .putExtra("tugas_user", nilai))
                            }
                        })
                        val recyclerView1 = findViewById<RecyclerView>(R.id.rvnilai_tugas)
                        recyclerView1.adapter = adapter
                    }
                    override fun onFailure(call: Call<ArrayList<TugasUserModel>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }
}