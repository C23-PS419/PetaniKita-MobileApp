package com.c23ps419.petanikita.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.databinding.ActivityMainBinding
import com.c23ps419.petanikita.ui.main.home.HomeFragment
import com.c23ps419.petanikita.ui.main.profile.ProfileFragment
import com.c23ps419.petanikita.ui.main.scan.ScanFragment

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val mainBinding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        loadFragment(HomeFragment())

        mainBinding?.bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                mainBinding?.bottomNavigationView?.menu?.findItem(R.id.bottomNavigationView_home)?.itemId -> {
                    loadFragment(HomeFragment())
                    true
                }
                mainBinding?.bottomNavigationView?.menu?.findItem(R.id.bottomNavigationView_scan)?.itemId -> {
                    loadFragment(ScanFragment())
                    true
                }
                mainBinding?.bottomNavigationView?.menu?.findItem(R.id.bottomNavigationView_profile)?.itemId -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        mainBinding?.frameContainer?.id?.let {
            supportFragmentManager.beginTransaction()
                .replace(it, fragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}