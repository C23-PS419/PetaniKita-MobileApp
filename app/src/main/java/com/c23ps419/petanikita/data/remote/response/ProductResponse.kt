package com.c23ps419.petanikita.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

@Parcelize
data class ProductResponse(

	@field:SerializedName("data")
	val data: List<Product?>? = null
) : Parcelable


@Parcelize
data class Product(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Timestamp? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable
