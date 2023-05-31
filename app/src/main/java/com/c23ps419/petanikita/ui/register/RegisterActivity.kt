package com.c23ps419.petanikita.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
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
            val phone = registerBinding?.edRegisterPhone?.text.toString()
            val password = registerBinding?.edRegisterPassword?.text.toString()

            viewModel.userRegister(name, email, phone, password).observe(this){result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            showCenteredToast("Pendaftaran berhasil.\nSilahkan masuk menggunakan akun anda.")
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showCenteredToast(result.error)
                        }
                    }
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun showCenteredToast(text: String){
        val toast = Toast.makeText(this, text, Toast.LENGTH_LONG)
        val v: TextView? = toast.view?.findViewById(android.R.id.message)
        if( v != null) v.gravity = Gravity.CENTER
        toast.show()
    }

    private fun showLoading(state: Boolean) {
        registerBinding?.progressCircular?.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityRegisterBinding = null
    }
}