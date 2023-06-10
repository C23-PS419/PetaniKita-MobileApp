package com.c23ps419.petanikita.ui.detection.detectionresult

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.c23ps419.petanikita.databinding.FragmentDetectionResultBinding
import com.c23ps419.petanikita.ui.detection.Disease
import java.io.File



class DetectionResultFragment : Fragment() {
    private var imageFile: File? = null
    private var diseaseArrayList: ArrayList<Disease>? = null
    private var _fragmentDetectionResultBinding: FragmentDetectionResultBinding? = null
    private val detectionResultBinding get() = _fragmentDetectionResultBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                imageFile = it.getSerializable(IMAGE_FILE, File::class.java)
            } else {
                imageFile= it.getSerializable(IMAGE_FILE) as File?
            }

            diseaseArrayList = it.getParcelableArrayList(EXTRA_HASIL_DETEKSI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentDetectionResultBinding = FragmentDetectionResultBinding.inflate(inflater, container, false)
        return detectionResultBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageFile?.let {image ->
            detectionResultBinding?.detectionImage?.setImageBitmap(BitmapFactory.decodeFile(image.path))
        }

        diseaseArrayList?.let {
            detectionResultBinding?.rvDetectionResult?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = DetectionResultAdapter(it)
            detectionResultBinding?.rvDetectionResult?.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentDetectionResultBinding = null
    }

    companion object {
        private const val IMAGE_FILE = "image_file"
        private const val EXTRA_HASIL_DETEKSI = "extra_hasil_deteksi"
        @JvmStatic
        fun newInstance(imageFile: File, detectionResult: ArrayList<Disease>) =
            DetectionResultFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(IMAGE_FILE, imageFile)
                    putParcelableArrayList(EXTRA_HASIL_DETEKSI, detectionResult)
                }
            }
    }
}