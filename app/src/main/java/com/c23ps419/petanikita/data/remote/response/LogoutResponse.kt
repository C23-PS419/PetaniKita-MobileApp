package com.c23ps419.petanikita.data.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("errors")
    val errors: Map<String, String>? = null
)
