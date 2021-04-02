package com.example.phoneauthentication.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val user_number: String,
    val user_name: String,
    val user_email: String
)
