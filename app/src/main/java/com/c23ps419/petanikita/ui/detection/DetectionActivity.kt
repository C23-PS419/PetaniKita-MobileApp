package com.c23ps419.petanikita.ui.detection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps419.petanikita.databinding.ActivityDetectionBinding
import com.c23ps419.petanikita.ui.detection.chooseimage.ChooseImageFragment

class DetectionActivity : AppCompatActivity() {

    private var _activityDetectionBinding: ActivityDetectionBinding? = null
    private val detectionBinding get() = _activityDetectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetectionBinding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(detectionBinding?.root)

        val jenisTanaman = intent.getStringExtra(EXTRA_JENIS_TANAMAN)
        detectionBinding?.title?.text = jenisTanaman

        detectionBinding?.backButton?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val chooseImageFragment = ChooseImageFragment()
        val bundle = Bundle()
        bundle.putString(ChooseImageFragment.EXTRA_JENIS_TANAMAN, jenisTanaman)
        detectionBinding?.frameContainerDetection?.id?.let {
            supportFragmentManager.beginTransaction()
                .replace(it, chooseImageFragment)
                .commit()
        }
    }

    companion object {
        const val EXTRA_JENIS_TANAMAN = "extra_jenis_tanaman"
    }
}