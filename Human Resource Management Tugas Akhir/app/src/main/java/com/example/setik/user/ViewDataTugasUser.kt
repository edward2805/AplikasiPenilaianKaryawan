package com.example.setik.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.setik.Adapter.TugasUserAdapter
import com.example.setik.MainActivity
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import com.example.setik.Retrofit.RetrofitTugasUser
import kotlinx.android.synthetic.main.activity_view_data_tugas_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewDataTugasUser : AppCompatActivity() {

    private val TAG2 : String = "ViewDataTugasUser"

    private val api by lazy { RetrofitTugasUser().endpoint }
    lateinit var tugasUserAdapter: TugasUserAdapter
    private lateinit var listDataUser : RecyclerView
    var listTugas = ArrayList<TugasUserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data_tugas_user)

        val kembali = findViewById<ImageView>(R.id.kembali_data_user)
        kembali.setOnClickListener {
            Intent(this@ViewDataTugasUser ,MainActivity::class.java).also {
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
            api.TugasUser(
                it.toInt()
            )
                .enqueue(object : Callback<ArrayList<TugasUserModel>>{
                    override fun onResponse(
                        call: Call<ArrayList<TugasUserModel>>,
                        response: Response<ArrayList<TugasUserModel>>
                    ) {
                        val responseCode = response.code().toString()
                        response.body()?.let { listTugas = it}
                        val adapter = TugasUserAdapter(listTugas, object : TugasUserAdapter.OnAdapterListener{
                            override fun onRead(tugas: TugasUserModel) {
                                startActivity(
                                    Intent(this@ViewDataTugasUser, DetailDataTugasUser::class.java)
                                        .putExtra("tugas_user", tugas))
                            }
                        })
                        rvTugasUser.adapter = adapter
                    }
                    override fun onFailure(call: Call<ArrayList<TugasUserModel>>, t: Throwable) {
                    }
                })
        }
    }
}