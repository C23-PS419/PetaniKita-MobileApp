package com.c23ps419.petanikita.ui.main.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c23ps419.petanikita.databinding.FragmentScanBinding


class ScanFragment : Fragment() {

    private var _fragmentScanBinding: FragmentScanBinding? = null
    private val scanBinding get() = _fragmentScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentScanBinding = FragmentScanBinding.inflate(inflater, container, false)
        return scanBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentScanBinding = null
    }

}