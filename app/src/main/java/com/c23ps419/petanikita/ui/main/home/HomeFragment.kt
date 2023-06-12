package com.c23ps419.petanikita.ui.main.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.remote.response.Product
import com.c23ps419.petanikita.data.Result
import com.c23ps419.petanikita.databinding.FragmentHomeBinding
import com.c23ps419.petanikita.ui.detailProduct.DetailProductActivity
import com.c23ps419.petanikita.utils.ViewModelFactoryDatabase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class HomeFragment : Fragment() {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _fragmentHomeBinding

    private lateinit var adapter: ProductAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactoryDatabase(UserPreferences.getInstance(requireContext().dataStore), requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter(
            object : ProductAdapter.OnItemClickListener{
                override fun onItemClick(product: Product) {
                    val intent = Intent(requireContext(), DetailProductActivity::class.java)
                    intent.putExtra("PRODUCT", product)
                    startActivity(intent)
                }
            }
        )

        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) // Mengatur 2 kolom
        homeBinding?.rvStories?.layoutManager = layoutManager
        homeBinding?.rvStories?.adapter = adapter

        viewModel.allProducts().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        // TO DO
                    }
                    is Result.Success -> {
                        val productList = result.data
                        adapter.setData(productList as List<Product>?)
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            requireContext(),
                            Log.e("HomeFragment","Fetching Data Error"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentHomeBinding = null
    }
}



//class HomeFragment : Fragment() {
//
//    private var _fragmentHomeBinding: FragmentHomeBinding? = null
//    private val homeBinding get() = _fragmentHomeBinding
//
//    private lateinit var adapter: ProductAdapter
//    private val homeViewModel: HomeViewModel by viewModels {
//        ViewModelFactoryDatabase(UserPreferences.getInstance(requireContext().dataStore), requireActivity().application)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
//        return homeBinding?.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        homeViewModel.product.observe(viewLifecycleOwner) {
//            setupAdapter()
//        }
//    }
//
//    private fun setupAdapter() {
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        homeBinding?.rvStories?.layoutManager = layoutManager
//
//        adapter = ProductAdapter()
//
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _fragmentHomeBinding = null
//    }
//
//}