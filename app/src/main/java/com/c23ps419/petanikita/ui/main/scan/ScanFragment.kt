package com.c23ps419.petanikita.ui.main.scan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps419.petanikita.databinding.FragmentScanBinding
import com.c23ps419.petanikita.ui.detection.DetectionActivity


class ScanFragment : Fragment() {

    private var _fragmentScanBinding: FragmentScanBinding? = null
    private val scanBinding get() = _fragmentScanBinding

    private val listJenisTanaman = listOf("Padi", "Jagung", "Ubi Jalar", "Tomat", "Kentang")

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
        val adapter = JenisAdapter(listJenisTanaman)

        scanBinding?.rvJenisTanaman?.adapter = adapter

        adapter.setOnItemClickCallback(object : JenisAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                showSelected(data)
            }
        })
    }

    private fun showSelected(data: String) {
        val intent = Intent(requireActivity(), DetectionActivity::class.java)
        intent.putExtra(DetectionActivity.EXTRA_JENIS_TANAMAN, data)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentScanBinding = null
    }

}