package com.c23ps419.petanikita.ui.main.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c23ps419.petanikita.databinding.CardItemJenisTanamanBinding

class JenisAdapter(private val listJenisTanaman: List<String>): RecyclerView.Adapter<JenisAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder (var binding: CardItemJenisTanamanBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemJenisTanamanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listJenisTanaman.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jenisTanaman = listJenisTanaman[position]

        holder.binding.tvJenisTanaman.text = jenisTanaman

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listJenisTanaman[position])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}