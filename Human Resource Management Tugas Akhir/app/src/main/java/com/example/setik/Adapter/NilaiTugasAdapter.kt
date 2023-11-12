package com.example.setik.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import kotlinx.android.synthetic.main.adapter_nilai_tugas.view.tv_nilai_tugas
import kotlinx.android.synthetic.main.adapter_nilai_tugas.view.tv_site_name_nilai_tugas
import kotlinx.android.synthetic.main.adapter_nilai_tugas.view.tv_tt_number_nilai_tugas

class NilaiTugasAdapter(
    val NilaiTugasUser: ArrayList<TugasUserModel>,
    val listener: OnAdapterListener
    ): RecyclerView.Adapter<NilaiTugasAdapter.PostViewHolder>()
{
    inner class PostViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        fun bind(tugasUserModel: TugasUserModel){
            with(itemView){
                val site_name = "Site Name : ${tugasUserModel.site_name}"
                val tt_number = "TT Number : ${tugasUserModel.tt_number}"
                val nilai = "Nilai : ${tugasUserModel.nilai}"

                tv_site_name_nilai_tugas.text = site_name
                tv_tt_number_nilai_tugas.text= tt_number
                tv_nilai_tugas.text= nilai
            }
        }
    }


    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ) : PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_nilai_tugas, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: NilaiTugasAdapter.PostViewHolder, position: Int) {
        holder.bind(NilaiTugasUser[position])

        holder.itemView.setOnClickListener{
            listener.onRead(NilaiTugasUser[position])
        }
    }

    override fun getItemCount()= NilaiTugasUser.size

    interface OnAdapterListener{
        fun onRead(nilai: TugasUserModel)
    }
}