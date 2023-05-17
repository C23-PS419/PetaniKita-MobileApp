package com.c23ps419.petanikita.ui.mainhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps419.petanikita.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val mainBinding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}