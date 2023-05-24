package com.c23ps419.petanikita.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.c23ps419.petanikita.data.Result
import com.c23ps419.petanikita.databinding.ActivityRegisterBinding
import com.c23ps419.petanikita.ui.login.LoginActivity
import com.c23ps419.petanikita.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private var _activityRegisterBinding: ActivityRegisterBinding? = null
    private val registerBinding get() = _activityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels{
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding?.root)

        registerBinding?.tvMasuk?.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerBinding?.myButton?.setOnClickListener {
            val name = registerBinding?.edRegisterName?.text.toString()
            val email = registerBinding?.edRegisterEmail?.text.toString()
            val password = registerBinding?.edRegisterPassword?.text.toString()

            viewModel.userRegister(name, email, password).observe(this){result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
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

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityRegisterBinding = null
    }
}