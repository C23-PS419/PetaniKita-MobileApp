package com.c23ps419.petanikita.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps419.petanikita.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _activityLoginBinding: ActivityLoginBinding? = null
    private val loginBinding get() = _activityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityLoginBinding = null
    }
}