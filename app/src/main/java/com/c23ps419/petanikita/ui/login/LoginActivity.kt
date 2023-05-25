package com.c23ps419.petanikita.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.c23ps419.petanikita.data.Result
import com.c23ps419.petanikita.databinding.ActivityLoginBinding
import com.c23ps419.petanikita.ui.mainhome.MainActivity
import com.c23ps419.petanikita.ui.register.RegisterActivity
import com.c23ps419.petanikita.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private var _activityLoginBinding: ActivityLoginBinding? = null
    private val loginBinding get() = _activityLoginBinding

    private val viewModel: LoginViewModel by viewModels{
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding?.root)

        loginBinding?.tvMasuk?.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBinding?.myButton?.setOnClickListener {
            val email = loginBinding?.edLoginEmail?.text.toString()
            val password = loginBinding?.edLoginPassword?.text.toString()

            viewModel.userLogin(email, password).observe(this){result ->
                if (result != null){
                    when (result){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            //TODO onLogin save the token and session to local data
                            //TODO flags for activity
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        loginBinding?.progressCircular?.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityLoginBinding = null
    }
}