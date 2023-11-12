package com.example.setik.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.setik.Model.TugasUserModel
import com.example.setik.R
import kotlinx.android.synthetic.main.adapter_tugas_selesai.view.*

class TugasSelesaiAdapter (
    val TugasUserSelesai: ArrayList<TugasUserModel>,
    val listener : OnAdapterListener
    ): RecyclerView.Adapter<TugasSelesaiAdapter.PostViewHolder>()
{
    inner class PostViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        fun bind(tugasUserModel: TugasUserModel){
            with(itemView){
                val site_name = "Site Name   : ${tugasUserModel.site_name}"
                val tt_number = "TT Number      : ${tugasUserModel.tt_number}"
                val status = "Status               : ${tugasUserModel.status}"

                tv_site_name_user_selesai.text = site_name
                tv_ttnumber_user_selesai.text = tt_number
                tv_status_user_selesai.text = status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_tugas_selesai, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(TugasUserSelesai[position])


        holder.itemView.setOnClickListener{
            listener.onRead(TugasUserSelesai[position])

        }
    }

    override fun getItemCount()= TugasUserSelesai.size

    interface OnAdapterListener {
        fun onRead(tugas: TugasUserModel)
    }
}