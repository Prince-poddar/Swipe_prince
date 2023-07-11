package com.example.swipe.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swipe.R
import com.example.swipe.databinding.ItemProductsLayoutBinding
import com.example.swipe.networking.response.ProductResponse

class ProductAdapter(
    private val context: Context,
    private val productList: ArrayList<ProductResponse>,

    ) :
    RecyclerView.Adapter<ProductAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val bindingLayout =
            ItemProductsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bindingLayout)
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class Holder(val binding: ItemProductsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var productItem: ProductResponse = productList[position]
        if (productItem != null) {
            holder.binding.ivPoster.let {
                if (productItem.image!=null && productItem.image!="") {
                    Glide.with(context).load(productItem.image).into(it)
                }
            }
            holder.binding.tvName.text = (productItem.productName)
            holder.binding.tvType.text = (productItem.productType)
            holder.binding.tvPrice.text = (productItem.price).toString()
            holder.binding.tvTax.text = (productItem.tax).toString()


        }
    }
}