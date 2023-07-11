package com.example.swipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipe.ProductActivity
import com.example.swipe.databinding.FragmentProductBinding
import com.example.swipe.networking.Status
import com.example.swipe.networking.response.ProductResponse
import com.example.swipe.ui.adapter.ProductAdapter
import com.example.swipe.ui.viewModels.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private var mainProductList = ArrayList<ProductResponse>()
    private var productList = ArrayList<ProductResponse>()
    private lateinit var adapter: ProductAdapter
    private val productViewModel: ProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set title
        activity?.title="Products"

        //set search menu
        setHasOptionsMenu(true)

        //initialize adapter
        binding.rvProducts.setItemViewCacheSize(0)

        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(requireContext(), productList)
        binding.rvProducts.adapter = adapter

        //click listener
        initClicked()

        //observer
        setUpObservers()
    }

    private fun initClicked() {
        binding.addProductFab.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    com.example.swipe.R.id.action_productFragment_to_addProductFragment,
                )

        }
    }

    override fun onResume() {
        super.onResume()
        productViewModel.getProductList()
    }

    private fun setUpObservers() {
        productViewModel.productResponse.observe(viewLifecycleOwner) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        (activity as ProductActivity).hideProgressBar()
                        resource.data?.let { response ->
                            productResponseData(response)

                        }
                    }

                    Status.ERROR -> {
                        (activity as ProductActivity).hideProgressBar()
                    }

                    Status.LOADING -> {
                        (activity as ProductActivity).showProgressBar()
                    }
                }
            }
        }
    }

    private fun productResponseData(response: List<ProductResponse>) {
        mainProductList.clear()
        if (response.isNotEmpty()) {
            mainProductList.addAll(response)
            productList.addAll(response)
        }
        adapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(com.example.swipe.R.menu.search_menu, menu)
        val searchItem = menu.findItem(com.example.swipe.R.id.actionSearch)
        val searchView: androidx.appcompat.widget.SearchView? =
            searchItem.actionView as androidx.appcompat.widget.SearchView?
        searchView?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText!="") {
                    filter(newText)
                }else{
                    onResume()
                }
                return false
            }
        })
        searchView?.setOnCloseListener {
            productViewModel.getProductList()
            false
        }
    }


    private fun filter(text: String) {
           val filteredList: ArrayList<ProductResponse> = ArrayList<ProductResponse>()

            for (item in mainProductList) {
                if (item.productName?.toLowerCase()
                        ?.contains(text.lowercase(Locale.getDefault())) == true
                ) {

                    filteredList.add(item)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
                productList.clear()

            } else {
                productList.clear()
                productList.addAll(filteredList)


            }
            adapter.notifyDataSetChanged()


    }
}