package com.c23ps419.petanikita.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.data.remote.response.Product
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class ProductAdapter(private val listener: OnItemClickListener? = null) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var data: List<Product>? = null

    fun setData(newData: List<Product>?) {
        val diffCallback = ProductDiffCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data?.get(position)
        product?.let {
            holder.bind(product)
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    inner class ProductViewHolder(
        itemView: View,
        private val listener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {

        private val avatarImageView: ImageView = itemView.findViewById(R.id.iv_item_photo)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_item_name)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_item_price)

        fun bind(product: Product) {
            val formattedPrice = product.price?.let { formatRupiah(it) }
            nameTextView.text = product.name
            priceTextView.text = formattedPrice

            val imageUrl = product.images?.get(0)

            Glide.with(itemView.context)
                .load(imageUrl)
                .into(avatarImageView)


            itemView.setOnClickListener {
                listener?.onItemClick(product)
            }
        }
    }

    private fun formatRupiah(price: Int): String {
        val localeID = Locale("in", "ID")
        val currencyFormat = DecimalFormat.getCurrencyInstance(localeID) as DecimalFormat
        val decimalFormatSymbols = DecimalFormatSymbols(localeID)
        decimalFormatSymbols.currencySymbol = "Rp "
        currencyFormat.decimalFormatSymbols = decimalFormatSymbols
        currencyFormat.minimumFractionDigits = 0

        return currencyFormat.format(price)
    }

    private class ProductDiffCallback(
        private val oldList: List<Product>?,
        private val newList: List<Product>?
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList?.size ?: 0
        override fun getNewListSize(): Int = newList?.size ?: 0

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList?.get(oldItemPosition)
            val newProduct = newList?.get(newItemPosition)
            return oldProduct?.id == newProduct?.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList?.get(oldItemPosition)
            val newProduct = newList?.get(newItemPosition)
            return oldProduct == newProduct
        }
    }
}


