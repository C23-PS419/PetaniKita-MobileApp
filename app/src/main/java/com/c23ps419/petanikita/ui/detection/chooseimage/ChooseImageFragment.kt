package com.c23ps419.petanikita.ui.detection.chooseimage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.databinding.FragmentChooseImageBinding
import com.c23ps419.petanikita.ml.RldcMobilenetV11Default1
import com.c23ps419.petanikita.ui.detection.Disease
import com.c23ps419.petanikita.ui.detection.detectionresult.DetectionResultFragment
import com.c23ps419.petanikita.utils.createCustomTempFile
import com.c23ps419.petanikita.utils.uriToFile
import org.tensorflow.lite.support.image.TensorImage
import java.io.File

class ChooseImageFragment : Fragment() {

    private var _fragmentChooseImageBinding: FragmentChooseImageBinding? = null
    private val chooseImageBinding get() = _fragmentChooseImageBinding
    private var imageFile: File? = null

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            imageFile = myFile
            myFile.let { file ->
                val bitmap = BitmapFactory.decodeFile(file.path)
                chooseImageBinding?.detectionImage?.setImageBitmap(bitmap)
                chooseImageBinding?.detectionImage?.visibility = View.VISIBLE
                chooseImageBinding?.buttonStartDetection?.visibility = View.VISIBLE
                chooseImageBinding?.tvDetectionImage?.visibility = View.INVISIBLE
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                imageFile = myFile
                val bitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(uri))
                chooseImageBinding?.detectionImage?.setImageBitmap(bitmap)
                chooseImageBinding?.detectionImage?.visibility = View.VISIBLE
                chooseImageBinding?.buttonStartDetection?.visibility = View.VISIBLE
                chooseImageBinding?.tvDetectionImage?.visibility = View.INVISIBLE
            }
        }
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){granted ->
        if (granted) {
            startTakePhoto()
        } else {
            Toast.makeText(requireContext(), "Permission Denied !! Try again", Toast.LENGTH_SHORT).show()
        }
    }

    private var jenisTanaman: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            jenisTanaman = arguments?.getString(EXTRA_JENIS_TANAMAN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentChooseImageBinding = FragmentChooseImageBinding.inflate(inflater, container, false)
        return chooseImageBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseImageBinding?.openCamera?.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                startTakePhoto()
            } else {
                requestPermission.launch(android.Manifest.permission.CAMERA)
            }
        }

        chooseImageBinding?.openGallery?.setOnClickListener {
            startGallery()
        }

        chooseImageBinding?.buttonStartDetection?.setOnClickListener {
            imageFile?.let { file ->
                val bitmap = BitmapFactory.decodeFile(file.path)
                val detectionResult = outputGenerator(bitmap)

                val detectionResultFragment = DetectionResultFragment.newInstance(file, detectionResult)
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_container_detection, detectionResultFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                Log.d("ChooseImageBinding", "Sending Image ${file.name}")
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.c23ps419.petanikita",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun outputGenerator(decodeFile: Bitmap): ArrayList<Disease> {
        val model = RldcMobilenetV11Default1.newInstance(requireContext())

        val bitmap = decodeFile.copy(Bitmap.Config.ARGB_8888, true)
        val image = TensorImage.fromBitmap(bitmap)

        val outputs = model.process(image)

        val sortedOutput = outputs.probabilityAsCategoryList
            .apply {sortByDescending { it.score } }
            .map {
                Disease(
                    it.label,
                    it.score
                )
            }
//        Toast.makeText(requireContext(), "${highProbabilitiesOutput.label} with ${highProbabilitiesOutput.score}%", Toast.LENGTH_LONG).show()

        model.close()
        return ArrayList(sortedOutput)
    }

    companion object {
        var EXTRA_JENIS_TANAMAN = "extra_jenis_tanaman"
    }
}