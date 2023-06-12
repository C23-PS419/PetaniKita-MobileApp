package com.c23ps419.petanikita.ui.detailProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.c23ps419.petanikita.R
import com.c23ps419.petanikita.data.remote.response.Product
import com.c23ps419.petanikita.databinding.ActivityDetailProductBinding
import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DetailProductActivity : AppCompatActivity() {
    private var _activityDetailBinding:ActivityDetailProductBinding? = null
    private val detailBinding get() = _activityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(detailBinding?.root)

        @Suppress("DEPRECATION")
        val product = intent.getParcelableExtra<Product>("PRODUCT")


        detailBinding?.tvDetailProductTitle?.text = product?.name
        detailBinding?.tvDetailProductStock?.text = "Stok : " + product?.stock.toString()
//        binding?.let { image ->
//            Glide.with(this)
//                .load(story?.photoUrl)
//                .into(image.ivDetailPhoto)
//        }
        detailBinding?.ivDetailProductPhoto?.setImageResource(R.drawable.baseline_person_24)
        detailBinding?.tvDetailProductPrice?.text = product?.price?.let { formatRupiah(it) }
//        detailBinding?.tvDetailProductDescription?.text = product?.createdAt
        val formattedTimeAgo = formatTimeAgo(product?.createdAt ?: Timestamp(0))
        detailBinding?.tvDetailProductCreated?.text = formattedTimeAgo
        detailBinding?.tvDetailProductDescription?.text = product?.description
        detailBinding?.tvDetailProductSeller?.text = product?.user?.name

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

fun formatTimeAgo(createdAt: Timestamp): String {
    val currentTime = System.currentTimeMillis()
    val elapsedTime = currentTime - createdAt.time

    val days = TimeUnit.MILLISECONDS.toDays(elapsedTime)
    val weeks = days / 7

    return when {
        weeks >= 1 -> "$weeks week${if (weeks > 1) "s" else ""} ago"
        days >= 1 -> "$days day${if (days > 1) "s" else ""} ago"
        else -> "Today"
    }
}