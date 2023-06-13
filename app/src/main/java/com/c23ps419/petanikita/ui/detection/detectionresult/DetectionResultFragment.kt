package com.c23ps419.petanikita.ui.detection.detectionresult

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageFile?.let {image ->
            detectionResultBinding?.detectionImage?.setImageBitmap(BitmapFactory.decodeFile(image.path))
        }

        diseaseArrayList?.let { disease ->
            detectionResultBinding?.tvLabel1?.text = disease[0].label
            detectionResultBinding?.tvProbability1?.text = disease[0].probability.times(100).toInt().toString() + "%"

            detectionResultBinding?.tvLabel2?.text = disease[1].label
            detectionResultBinding?.tvProbability2?.text = disease[1].probability.times(100).toInt().toString() + "%"

            detectionResultBinding?.tvLabel3?.text = disease[2].label
            detectionResultBinding?.tvProbability3?.text = disease[2].probability.times(100).toInt().toString() + "%"
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