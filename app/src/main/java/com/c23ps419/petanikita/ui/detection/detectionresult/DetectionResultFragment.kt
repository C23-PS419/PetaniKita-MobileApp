package com.c23ps419.petanikita.ui.detection.detectionresult

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c23ps419.petanikita.databinding.FragmentDetectionResultBinding
import java.io.File

private const val IMAGE_FILE = "image_file"

class DetectionResultFragment : Fragment() {
    private var imageFile: File? = null
    private var _fragmentDetectionResultBinding: FragmentDetectionResultBinding? = null
    private val detectionResultBinding get() = _fragmentDetectionResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                imageFile = it.getSerializable(IMAGE_FILE, File::class.java)
            } else {
                @Suppress("DEPRECATION")
                imageFile= it.getSerializable(IMAGE_FILE) as File?
            }
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentDetectionResultBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(imageFile: File) =
            DetectionResultFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(IMAGE_FILE, imageFile)
                }
            }
    }
}