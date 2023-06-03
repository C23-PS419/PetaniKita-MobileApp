package com.c23ps419.petanikita.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.data.Result
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.remote.response.User
import com.c23ps419.petanikita.databinding.FragmentProfileBinding
import com.c23ps419.petanikita.ui.login.LoginActivity
import com.c23ps419.petanikita.utils.ViewModelFactoryDatabase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class ProfileFragment : Fragment() {

    private var _fragmentProfileBinding: FragmentProfileBinding? = null
    private val profileBinding get() = _fragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactoryDatabase(UserPreferences.getInstance(requireContext().dataStore), requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return profileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileBinding?.logoutBtn?.setOnClickListener {
            viewModel.userLogout().observe(viewLifecycleOwner){result ->
                if (result != null) when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val intent = Intent(requireActivity(),LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e("ProfileFragment","Logout Error")
                        val intent = Intent(requireActivity(),LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner){user ->
            showProfile(user)
        }
    }

    private fun showProfile(user: User?){
        user?.let {
            profileBinding?.tvProfileName?.text = (if (!it.name.isNullOrEmpty()) it.name else R.string.name).toString()
            profileBinding?.tvProfileEmail?.text = (if (!it.email.isNullOrEmpty()) it.email else R.string.email).toString()
            profileBinding?.tvProfilePhone?.text = (if (!it.phone.isNullOrEmpty()) it.phone else R.string.phone_number).toString()
        }
        profileBinding?.tvProfileName?.isVisible = true
        profileBinding?.tvProfileEmail?.isVisible = true
        profileBinding?.tvProfilePhone?.isVisible = true
    }

    private fun showLoading(state: Boolean) {
        profileBinding?.logoutBtn?.isVisible = state
        profileBinding?.progressCircular?.isVisible = !state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentProfileBinding = null
    }
}