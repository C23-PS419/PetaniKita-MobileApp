package com.c23ps419.petanikita.ui.detection

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease (
    var label: String,
    var probability: Float
): Parcelable