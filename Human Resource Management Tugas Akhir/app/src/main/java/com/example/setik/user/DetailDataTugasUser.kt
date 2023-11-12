package com.example.setik.user

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import com.example.setik.Retrofit.RetrofitTugasUser
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_detail_data_tugas_user.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


class DetailDataTugasUser : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1;
    private val REQUEST_MANAGE_EXTERNAL_STORAGE = 1;
    private var selectedImageUri: Uri? = null
    private lateinit var btn_gambar_project : Button

    private lateinit var button_edit : MaterialButton
    private lateinit var tv_det_status_user : TextView
    private val api by lazy { RetrofitTugasUser().endpoint }

    private val dataTugas by lazy { intent.getSerializableExtra("tugas_user") as TugasUserModel }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_data_tugas_user)
        button_edit = findViewById(R.id.btn_edit_submit)
        tv_det_status_user = findViewById(R.id.tv_status_close)
        btn_gambar_project = findViewById(R.id.btn_gambar_project)
        btn_gambar_project.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        setupView()
        button_edit.setOnClickListener{
            val id_tugas = tv_det_id_tugas_user.text.toString()
            val status = tv_det_status_user.text.toString()
            val keterangan = EtKeterangan.text.toString()
            val gambar = File(getRealPathFromURI(selectedImageUri!!))

            setupListener(gambar, id_tugas, status, keterangan)
        }

        if (Environment.isExternalStorageManager()) {
            // Izin sudah diberikan, Anda dapat mengakses file penyimpanan eksternal di sini
        } else {
            // Izin belum diberikan, ajukan izin secara dinamis
            val manageExternalStorageIntent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", packageName, null)
            manageExternalStorageIntent.data = uri
            startActivityForResult(manageExternalStorageIntent, REQUEST_MANAGE_EXTERNAL_STORAGE)
        }

        val kembali = findViewById<ImageView>(R.id.kembali_edit_tugas_user)
        kembali.setOnClickListener {
            Intent(this@DetailDataTugasUser , ViewDataTugasUser::class.java).also {
                startActivity(it)
            }
        }
    }

    // Periksa apakah izin MANAGE_EXTERNAL_STORAGE sudah diberikan


    // Tangkap hasil permintaan izin di onActivityResult
    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null){
            selectedImageUri = data.data
            imageViewTugas.setImageURI(selectedImageUri)
        }
        if (requestCode == REQUEST_MANAGE_EXTERNAL_STORAGE) {
            if (Environment.isExternalStorageManager()) {
                // Izin telah diberikan, Anda dapat mengakses file penyimpanan eksternal di sini
            } else {
                // Izin ditolak oleh pengguna, berikan penanganan yang sesuai
            }
        }
    }

// Pemilihan gambar dari galeri

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(projection[0])
        val path = cursor?.getString(columnIndex ?: 0)
        cursor?.close()
        return path ?: ""
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
        tv_det_start_tugas.setText(dataTugas.start_tugas)
        tv_det_end_tugas.setText(dataTugas.end_tugas)
        tv_det_durasi.setText(dataTugas.total_durasi)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupListener(
        gambar: File,
        id_tugas: String,
        status: String,
        keterangan: String
    ){
        val id_tugas = id_tugas.toRequestBody("text/plain".toMediaType())
        val status = status.toRequestBody("text/plain".toMediaType())
        val keterangan = keterangan.toRequestBody("text/plain".toMediaType())
        val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), gambar)
        val gambarPart = MultipartBody.Part.createFormData("gambar", gambar.name, imageRequestBody)


            api.update(gambarPart, id_tugas, status, keterangan)
                .enqueue(object : Callback<TugasUserModel>{
                    override fun onResponse(
                        call: Call<TugasUserModel>,
                        response: Response<TugasUserModel>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(this@DetailDataTugasUser, "Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent
                                (this@DetailDataTugasUser, ViewDataTugasUser::class.java))
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<TugasUserModel>, t: Throwable) {
                        Toast.makeText(this@DetailDataTugasUser, "gagal", Toast.LENGTH_SHORT).show()
                    }

                })
            }
    }