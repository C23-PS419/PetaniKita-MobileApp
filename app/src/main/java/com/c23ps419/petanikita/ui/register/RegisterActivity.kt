package com.c23ps419.petanikita.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps419.petanikita.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private var _activityRegisterBinding: ActivityRegisterBinding? = null
    private val registerBinding get() = _activityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityRegisterBinding = null
    }
}