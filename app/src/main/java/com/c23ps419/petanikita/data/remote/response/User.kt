package com.c23ps419.petanikita.data.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserInformationResponse(
    @field:SerializedName("data")
    val data: User? = null,

    @field:SerializedName("message")
    val message: String? = null
)

@Parcelize
@Entity(tableName = "user")
data class User(

    @field:ColumnInfo("id")
    @field:PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    val id: Int? = null,

    @field:ColumnInfo("updated_at")
    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:ColumnInfo("name")
    @field:SerializedName("name")
    val name: String? = null,

    @field:ColumnInfo("created_at")
    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:ColumnInfo("email_verified_at")
    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:ColumnInfo("email")
    @field:SerializedName("email")
    val email: String? = null,

    @field:ColumnInfo("phone")
    @field:SerializedName("phone")
    val phone: String? = null
):Parcelable
