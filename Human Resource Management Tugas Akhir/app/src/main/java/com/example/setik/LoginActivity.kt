package com.example.setik

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.setik.Model.ResponseLogin
import com.example.setik.Retrofit.RetrofitClient
import com.example.setik.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var binding : ActivityLoginBinding? = null
    private var user : String = ""
    private var pass : String = ""
    private lateinit var profil : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//      cek session
        profil = getSharedPreferences("login_session", MODE_PRIVATE)
        if (profil.getString("username", null) !=null ) {
        }


        binding!!.btnLogin.setOnClickListener{
            user = binding!!.etusername.text.toString()
            pass = binding!!.etpassword.text.toString()

            when{
                user == "" -> {
                    binding!!.etusername.error = "username tidak boleh kosong!!"
                }
                pass == "" -> {
                    binding!!.etpassword.error = "password tidak boleh kosong!!"
                }
                else -> {
                    binding!!.loading.visibility = View.VISIBLE
                    getData()
                }
            }
        }
    }

    private fun getData () {
        val api = RetrofitClient().getInstance()
        api.login(user, pass).enqueue(object : retrofit2.Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    if (response.body()?.response == true) {
//                      session Login
                        getSharedPreferences("login_session", MODE_PRIVATE)
                            .edit()
                            .putString("id_user", response.body()?.payload?.id)
                            .putString("username", response.body()?.payload?.username)
                            .putString("nama", response.body()?.payload?.nama)
                            .apply()

                        binding!!.loading.visibility = View.VISIBLE
                        SweetAlertDialog(this@LoginActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Selamat datang!")
                            .setContentText("Berhasil Login")
                            .setConfirmClickListener(
                                {
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                })
                            .show()

                    }else {
                        binding!!.loading.visibility = View.GONE
                        SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Silahkan Cek Username/Password Anda")
                            .show()
                    }
                }else{
                    SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Login Gagal, Terjadi Kesalahan!!")
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("Pesan Error", "${t.message}")
            }

        })
    }
}