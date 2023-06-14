package com.c23ps419.petanikita.ui.main.scan

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c23ps419.petanikita.databinding.CardItemJenisTanamanBinding

class JenisAdapter(private val listJenisTanaman: ArrayList<JenisTanaman>, private val listImplementedModel: List<String>): RecyclerView.Adapter<JenisAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder (var binding: CardItemJenisTanamanBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemJenisTanamanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listJenisTanaman.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jenisTanaman = listJenisTanaman[position]

        if (!listImplementedModel.contains(jenisTanaman.nama)){
            holder.binding.cardViewJenisTanaman.foreground = ColorDrawable(Color.parseColor("#906F6F6F"))
        }

        holder.binding.tvJenisTanaman.text = jenisTanaman.nama

        Glide.with(holder.itemView.context)
            .load(jenisTanaman.image)
            .into(holder.binding.imageJenisTanaman)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listJenisTanaman[position].nama)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}