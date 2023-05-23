package com.c23ps419.petanikita.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable