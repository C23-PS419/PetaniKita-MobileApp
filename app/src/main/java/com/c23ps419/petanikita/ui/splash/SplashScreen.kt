package com.c23ps419.petanikita.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.ui.login.LoginActivity
import com.c23ps419.petanikita.ui.main.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val preferences = UserPreferences.getInstance(dataStore)

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                val session = preferences.getUserSession().first()
                if (session) {
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 2000)
    }
}