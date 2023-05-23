package com.c23ps419.petanikita.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
    val data: User? = null,

    @field:SerializedName("message")
    val message: String? = null
)