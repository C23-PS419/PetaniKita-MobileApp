package com.c23ps419.petanikita.ui.main.scan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.databinding.FragmentScanBinding
import com.c23ps419.petanikita.ui.detection.DetectionActivity


class ScanFragment : Fragment() {

    private var _fragmentScanBinding: FragmentScanBinding? = null
    private val scanBinding get() = _fragmentScanBinding

    private val listJenisTanaman = listOf("Padi", "Jagung", "Tomat", "Kentang")
    private val listImplementedModel = listOf("Padi")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentScanBinding = FragmentScanBinding.inflate(inflater, container, false)
        return scanBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanBinding?.rvJenisTanaman?.layoutManager = GridLayoutManager(requireContext(), 2)
        getJenisTanamanData()
    }

    private fun getJenisTanamanData() {
        val adapter = JenisAdapter(getListJenisTanaman(), listImplementedModel)

        scanBinding?.rvJenisTanaman?.adapter = adapter

        adapter.setOnItemClickCallback(object : JenisAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                showSelected(data)
            }
        })
    }

    @SuppressLint("Recycle")
    private fun getListJenisTanaman(): ArrayList<JenisTanaman>{
        val images = resources.obtainTypedArray(R.array.images_jenis_tanaman)
        val listJenisTanamanForAdapter = ArrayList<JenisTanaman>()
        for (i in listJenisTanaman.indices){
            val jenisTanaman = JenisTanaman(listJenisTanaman[i], images.getResourceId(i, -1))
            listJenisTanamanForAdapter.add(jenisTanaman)
        }
        return listJenisTanamanForAdapter
    }

    private fun showSelected(data: String) {
        if (listImplementedModel.contains(data)){
            val intent = Intent(requireActivity(), DetectionActivity::class.java)
            intent.putExtra(DetectionActivity.EXTRA_JENIS_TANAMAN, data)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Fitur ini belum diterapkan. Harap kembali lagi nanti!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentScanBinding = null
    }

}