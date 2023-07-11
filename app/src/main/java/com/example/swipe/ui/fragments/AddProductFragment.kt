package com.example.swipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.swipe.ProductActivity
import com.example.swipe.R
import com.example.swipe.databinding.FragmentAddProductBinding
import com.example.swipe.networking.Status
import com.example.swipe.ui.viewModels.AddProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProductFragment:Fragment()
{
    private lateinit var binding: FragmentAddProductBinding
    private val addProductViewModel: AddProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentAddProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set title
        activity?.title="Add Product"

        val languages = resources.getStringArray(R.array.product_type)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.tvProductType.setAdapter(arrayAdapter)

        setUpObservers()

        binding.btnAddProduct.setOnClickListener {
           if ( isValid()){
               hitAddProductApi()
           }
        }
    }

    private fun hitAddProductApi() {
        addProductViewModel.addProduct(
            binding.etName.text.toString(),
            binding.tilProductType.editText?.text.toString(),
            binding.etSellingPrice.text.toString(),
            binding.etTax.text.toString(),

        )
    }

    private fun isValid():Boolean{
        if (binding.etName.text.toString().trim().isBlank()){
            binding.tilName.error="Product Name can't remain blank."
            return false
        }

        if (binding.tilProductType.editText?.text.toString().trim().isBlank()){
            binding.tilProductType.error="Please select Product Type."
            return false
        }

        if (binding.etSellingPrice.text.toString().trim().isBlank()){
            binding.tilSellingPrice.error="Product Price can't remain blank."
            return false
        }
        if (binding.etTax.text.toString().trim().isBlank()){
            binding.tilTax.error="Product Tax can't remain blank."
            return false
        }
        return true
    }

    private fun setUpObservers() {
        addProductViewModel.addProductResponse.observe(viewLifecycleOwner) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                          (activity as ProductActivity).hideProgressBar()
                        resource.data?.let { response ->
                            Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_LONG).show()
                            (activity as ProductActivity).onBackPressed()

                        }
                    }

                    Status.ERROR -> {
                         (activity as ProductActivity).hideProgressBar()
                        resource.data?.let { response ->
                            Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_LONG)
                        }

                    }

                    Status.LOADING -> {
                          (activity as ProductActivity).showProgressBar()
                    }
                }
            }
        }
    }

}