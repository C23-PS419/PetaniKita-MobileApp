package com.c23ps419.petanikita.ui.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.c23ps419.petanikita.data.Result
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.databinding.ActivityRegisterBinding
import com.c23ps419.petanikita.ui.login.LoginActivity
import com.c23ps419.petanikita.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class RegisterActivity : AppCompatActivity() {

    private var _activityRegisterBinding: ActivityRegisterBinding? = null
    private val registerBinding get() = _activityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels{
        ViewModelFactory(UserPreferences.getInstance(dataStore))
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

        setMyButtonEnable()
        registerBinding?.edRegisterName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        registerBinding?.edRegisterEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        registerBinding?.edRegisterPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        registerBinding?.edRegisterPhone?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setMyButtonEnable() {
        val name = registerBinding?.edRegisterName?.text?.toString()
        val email = registerBinding?.edRegisterEmail?.text?.toString()
        val password = registerBinding?.edRegisterPassword?.text?.toString()
        val phone = registerBinding?.edRegisterPhone?.text?.toString()
        registerBinding?.myButton?.isEnabled = isValidEmail(email) && (password?.length ?: 0) >= 8 && (name?.length ?: 0) > 0 && ((phone?.length ?: 0) >= 10 && (phone?.length ?: 0) <= 13)
    }

    private fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
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