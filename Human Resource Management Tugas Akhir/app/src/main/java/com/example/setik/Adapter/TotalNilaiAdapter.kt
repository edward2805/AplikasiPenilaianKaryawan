package com.example.setik.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import kotlinx.android.synthetic.main.adapter_total_nilai.view.tv_rata_tugas
import kotlinx.android.synthetic.main.adapter_total_nilai.view.tv_total_nilai
import kotlinx.android.synthetic.main.adapter_total_nilai.view.tv_total_tugas

class TotalNilaiAdapter (
    val TotalNilaiUser: ArrayList<TugasUserModel>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<TotalNilaiAdapter.PostViewHolder>()
{
    inner class PostViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        fun bind(tugasUserModel: TugasUserModel){
            with(itemView){
                val total_nilai = "Total Nilai : ${tugasUserModel.total_nilai}"
                val total_tugas = "Total Tugas : ${tugasUserModel.total_tugas}"
                val rata_tugas = "Rata-Rata Tugas : ${tugasUserModel.rata_rata_tugas}"

                tv_total_nilai.text = total_nilai
                tv_total_tugas.text= total_tugas
                tv_rata_tugas.text= rata_tugas
            }
        }
    }


    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ) : TotalNilaiAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_total_nilai, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: TotalNilaiAdapter.PostViewHolder, position: Int) {
        holder.bind(TotalNilaiUser[position])

        holder.itemView.setOnClickListener{
            listener.onReadNilaiTotal(TotalNilaiUser[position])
        }
    }

    override fun getItemCount()= TotalNilaiUser.size

    interface OnAdapterListener{
        fun onReadNilaiTotal(totalNilaiUser: TugasUserModel)
    }

}