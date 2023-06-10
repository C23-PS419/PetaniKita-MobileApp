package com.c23ps419.petanikita.ui.detection.detectionresult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c23ps419.petanikita.databinding.CardItemDetectionResultBinding
import com.c23ps419.petanikita.ui.detection.Disease

class DetectionResultAdapter(private val diseaseList: List<Disease>): RecyclerView.Adapter<DetectionResultAdapter.ViewHolder>() {

    class ViewHolder (var binding: CardItemDetectionResultBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemDetectionResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = diseaseList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = diseaseList[position]

        holder.binding.tvLabel.text = disease.label
        holder.binding.tvProbability.text = "${disease.probability.times(100)}%"


        if (disease.label == "Healthy"){
            holder.binding.tvDiseaseDescription.visibility = View.GONE
        } else {
            holder.binding.tvDiseaseDescription.text = "Press to search on Google"
        }
    }
}